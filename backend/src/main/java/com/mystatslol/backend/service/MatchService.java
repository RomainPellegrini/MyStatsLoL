package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.MatchSummaryDTO;
import com.mystatslol.backend.DTO.riotresponse.MatchRiotDTO;
import com.mystatslol.backend.entity.Match;
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

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MatchService {

    @Value("${riot.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://europe.api.riotgames.com";
    private final MatchRepository matchRepository;
    private final MatchParticipantRepository matchParticipantRepository;
    private final RiotApiClient riotApiClient;

    private static final Logger log = LoggerFactory.getLogger(MatchService.class);


    public MatchService(MatchRepository matchRepository, MatchParticipantRepository matchParticipantRepository, RiotApiClient riotApiClient) {
        this.matchRepository = matchRepository;
        this.matchParticipantRepository = matchParticipantRepository;
        this.riotApiClient = riotApiClient;
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


       /* if (matchRepository.existsByMatchId(matchId)) {
            log.info("Match {} already stored, skipping fetch.", matchId);
            return matchRepository.findById(matchId)
                    .orElseThrow(() -> new MatchNotFoundException(matchId));
        }*/

        // 3. Appel Riot pour le détail de la partie
        Map<String, Object> matchMap = riotApiClient.getMatchById(matchId);
        ObjectMapper mapper = new ObjectMapper();
        MatchRiotDTO matchDto = mapper.convertValue(matchMap,MatchRiotDTO.class);


        // 4. Mapping + persistance
       // Match match = mapAndSave(matchDto);
        //log.info("Match {} stored with {} participants.", matchId, match.getParticipants().size());

        return convertRiotMatchtoSummaryMatch(matchDto);
    }

    private MatchSummaryDTO convertRiotMatchtoSummaryMatch(MatchRiotDTO matchRiotDTO){
        String gameMode ="";
        switch (matchRiotDTO.info().queueId()){
            case 400 :
                gameMode = "Draft";
                break;
            case 420 :
                gameMode= "SoloQ";
                break;
            case 430 :
                gameMode= "Blind";
                break;
            case 440 :
                gameMode= "Flex";
                break;
            default:
                gameMode="other";
                break;
        }

        MatchSummaryDTO matchSummaryDTO = new MatchSummaryDTO(
                matchRiotDTO.metadata().matchId(),
                matchRiotDTO.info().gameCreation(),
                matchRiotDTO.info().gameDuration(),
                gameMode,
                null,
                null

        );

        return matchSummaryDTO;
    }
}
