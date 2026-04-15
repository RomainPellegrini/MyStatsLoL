package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.entity.Player;
import com.mystatslol.backend.repository.PlayerRepository;
import com.mystatslol.backend.riot.RiotApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional
public class PlayerService {

    @Value("${riot.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://europe.api.riotgames.com";
    private final PlayerRepository playerRepository;
    private final RiotApiClient riotApiClient;
    private final KafkaProducerService kafkaProducerService;
    public PlayerService(PlayerRepository playerRepository, RiotApiClient riotApiClient, KafkaProducerService kafkaProducerService) {
        this.playerRepository = playerRepository;
        this.riotApiClient = riotApiClient;
        this.kafkaProducerService = kafkaProducerService;
    }


    public PlayerDTO getPlayerByPuuid(String puuid) {

        Map<String, Object> body = riotApiClient.getAccountByPuuid(puuid);

        String name = (String) body.get("gameName");
        String tag = (String) body.get("tagLine");

        Player p = new Player();
        p.setPuuid(puuid);
        p.setGameName(name);
        p.setTagLine(tag);
        playerRepository.save(p);
        PlayerDTO playerDTO = new PlayerDTO(puuid,name, tag);
        kafkaProducerService.sendPlayer(playerDTO);
        return playerDTO;
    }

    public PlayerDTO getPlayerByName(String gameName, String tagLine) {

        Map<String, Object> body = riotApiClient.getAccountByRiotId(gameName, tagLine);

        String puuid = (String) body.get("puuid");

        Player p = new Player();
        p.setPuuid(puuid);
        p.setGameName(gameName);
        p.setTagLine(tagLine);
        playerRepository.save(p);

        PlayerDTO playerDTO = new PlayerDTO(puuid,gameName, tagLine);
        kafkaProducerService.sendPlayer(playerDTO);
        return playerDTO;
    }

    public void deletePlayerByPuuid(String puuid) {
        if(playerRepository.existsById(puuid)) {
            playerRepository.deleteById(puuid);
        } else {
            throw new RuntimeException("Player with puuid : " + puuid + " not found.");
        }
    }

    private void sendToKafka(PlayerDTO player){

    }
}
