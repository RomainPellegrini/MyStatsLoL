package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.MatchParticipantDetailDTO;
import com.mystatslol.backend.DTO.MatchSummaryDTO;
import com.mystatslol.backend.DTO.ParticipantDTO;
import com.mystatslol.backend.DTO.riotresponse.MatchRiotDTO;
import com.mystatslol.backend.DTO.riotresponse.ParticipantRiotDTO;
import com.mystatslol.backend.entity.Match;
import com.mystatslol.backend.entity.MatchParticipant;
import com.mystatslol.backend.exception.MatchNotFoundException;
import com.mystatslol.backend.repository.MatchParticipantRepository;
import com.mystatslol.backend.repository.MatchRepository;
import com.mystatslol.backend.repository.PlayerRepository;
import com.mystatslol.backend.riot.RiotApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MatchService {

    @Value("${riot.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://europe.api.riotgames.com";
    private final MatchRepository matchRepository;
    private final MatchParticipantRepository matchParticipantRepository;
    private final RiotApiClient riotApiClient;
    private final KafkaProducerService kafkaProducerService;

    private static final Logger log = LoggerFactory.getLogger(MatchService.class);


    public MatchService(MatchRepository matchRepository, MatchParticipantRepository matchParticipantRepository, RiotApiClient riotApiClient, KafkaProducerService kafkaProducerService) {
        this.matchRepository = matchRepository;
        this.matchParticipantRepository = matchParticipantRepository;
        this.riotApiClient = riotApiClient;
        this.kafkaProducerService = kafkaProducerService;
    }


    public MatchSummaryDTO getLastMatch(String puuid) {

        List<String> matchIds = riotApiClient.getLastMatchsIds(puuid,1);
        if(matchIds.size()==0){
            throw new RuntimeException("No match found for this account");
        }else if(matchIds.size()!=1){
            throw new RuntimeException("Response contains more than 1 match");
        }
        String matchId = matchIds.get(0);


        log.info("Last matchId for puuid={} : {}", puuid, matchId);

        if (!matchRepository.existsByMatchId(matchId)) {
           return stockMatch(matchId,puuid);
        }


        Map<String, Object> matchMap = riotApiClient.getMatchById(matchId);
        ObjectMapper mapper = new ObjectMapper();
        MatchRiotDTO matchDto = mapper.convertValue(matchMap,MatchRiotDTO.class);
        MatchSummaryDTO matchSummaryDTO =  MatchSummaryDTO.from(matchDto, puuid);
        kafkaProducerService.sendMatch(matchSummaryDTO);
        return matchSummaryDTO;
    }

    private MatchSummaryDTO stockMatch(String matchId,String puuid){

        log.info("Match {} not found in DB, fetching from Riot API...", matchId);

        Map<String, Object> matchMap = riotApiClient.getMatchById(matchId);
        ObjectMapper mapper = new ObjectMapper();
        MatchRiotDTO matchDto = mapper.convertValue(matchMap, MatchRiotDTO.class);

        Match match = matchfromRiotDTO(matchDto);
        matchRepository.save(match);

        log.info("Match {} saved to DB.", matchId);

        return MatchSummaryDTO.from(matchDto, puuid);

    }

    public List<MatchSummaryDTO> getLast10Matches(String puuid) {

        List<String> matchIds = riotApiClient.getLastMatchsIds(puuid, 10);
        List<MatchSummaryDTO> result = new ArrayList<>();

        if(matchIds.size()==0){
            throw new RuntimeException("No match found for this account");
        }else if(matchIds.size()!=10){
            throw new RuntimeException("Response contains more than 10 matchs");
        }

        for(String matchId : matchIds){
            log.info("Processing matchId={}", matchId);
            if (!matchRepository.existsByMatchId(matchId)) {
                result.add(stockMatch(matchId,puuid));
            }else{
                Map<String, Object> matchMap = riotApiClient.getMatchById(matchId);
                ObjectMapper mapper = new ObjectMapper();
                MatchRiotDTO matchDto = mapper.convertValue(matchMap,MatchRiotDTO.class);
                result.add(MatchSummaryDTO.from(matchDto, puuid));
            }
        }
        if(result.size()!=10){
            throw new RuntimeException("The final return does not have 10 matches");
        }
        for (MatchSummaryDTO matchSummaryDTO : result){
            kafkaProducerService.sendMatch(matchSummaryDTO);
        }
        return result;
    }

    public List<MatchSummaryDTO> catchUpMatches(String puuid) {

        List<MatchSummaryDTO> result = new ArrayList<>();

        int start = 0;
        int count = 10;
        boolean stop = false;

        while (!stop) {

            List<String> matchIds = riotApiClient.getStartMatchsIds(puuid, start, count);

            if (matchIds.isEmpty()) {
                break;
            }

            for (String matchId : matchIds) {

                log.info("Checking matchId={}", matchId);

                if (matchRepository.existsByMatchId(matchId)) {
                    log.info("Match {} already exists in DB → stopping catch-up", matchId);
                    stop = true;
                    break;
                }

                result.add(stockMatch(matchId, puuid));
            }

            start += count;
        }
        for (MatchSummaryDTO matchSummaryDTO : result){
            kafkaProducerService.sendMatch(matchSummaryDTO);
        }
        return result;
    }

    private  Match matchfromRiotDTO(MatchRiotDTO dto) {
        Match match = new Match();
        match.setMatchId( dto.metadata().matchId());
        match.setGameCreation(dto.info().gameCreation());
        match.setGameDuration(dto.info().gameDuration());
        match.setGameVersion(dto.info().gameVersion());
        match.setQueueId(dto.info().queueId());
        match.setGameMode(dto.info().gameMode());

        List<MatchParticipant> participants = dto.info().participants()
                .stream()
                .map(p -> matchParticipantfromRiotDTO(p, match))
                .collect(Collectors.toList());

        match.setParticipants(participants);
        return match;
    }

    private MatchParticipant matchParticipantfromRiotDTO(ParticipantRiotDTO p, Match match) {
        MatchParticipant mp = new MatchParticipant();
        mp.setMatch(match);
        mp.setPuuid(p.puuid());
        mp.setRiotIdGameName(p.riotIdGameName());
        mp.setRiotIdTagline(p.riotIdTagline());

        mp.setChampionId(p.championId());
        mp.setChampionName(p.championName());
        mp.setChampLevel(p.champLevel());

        mp.setTeamId(p.teamId());
        mp.setTeamPosition(p.teamPosition());
        mp.setWin(p.win());

        mp.setKills(p.kills());
        mp.setDeaths(p.deaths());
        mp.setAssists(p.assists());

        mp.setGoldEarned(p.goldEarned());
        mp.setTotalDamageDealtToChampions(p.totalDamageDealtToChampions());

        mp.setItem0(p.item0());
        mp.setItem1(p.item1());
        mp.setItem2(p.item2());
        mp.setItem3(p.item3());
        mp.setItem4(p.item4());
        mp.setItem5(p.item5());
        mp.setItem6(p.item6());

        mp.setSummoner1Id(p.summoner1Id());
        mp.setSummoner2Id(p.summoner2Id());

        mp.setTotalMinionsKilled(p.totalMinionsKilled());
        return mp;
    }
}
