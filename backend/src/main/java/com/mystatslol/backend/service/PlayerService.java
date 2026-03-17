package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.entity.Player;
import com.mystatslol.backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PlayerService {

    @Value("${riot.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://europe.api.riotgames.com";
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    private Map constructURL(String url,RestTemplate restTemplate){

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = response.getBody();

        return body;
    }

    public PlayerDTO getPlayerByPuuid(String puuid) {

        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_URL + "/riot/account/v1/accounts/by-puuid/" + puuid;

        Map body = constructURL(url, restTemplate);

        String name = (String) body.get("gameName");
        String tag = (String) body.get("tagLine");
        Player p = new Player();
        p.setPuuid(puuid);
        p.setGameName(name);
        p.setTagLine(tag);
        playerRepository.save(p);
        return new PlayerDTO(puuid,name, tag);
    }

    public PlayerDTO getPlayerByName(String gameName, String tagLine) {

        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_URL + "/riot/account/v1/accounts/by-riot-id/" + gameName +"/"+tagLine;

        Map body = constructURL(url, restTemplate);

        String puuid = (String) body.get("puuid");

        Player p = new Player();
        p.setPuuid(puuid);
        p.setGameName(gameName);
        p.setTagLine(tagLine);
        playerRepository.save(p);

        return new PlayerDTO(puuid,gameName, tagLine);
    }

    public void deletePlayerByPuuid(String puuid) {
        if(playerRepository.existsById(puuid)) {
            playerRepository.deleteById(puuid);
        } else {
            throw new RuntimeException("Player with puuid : " + puuid + " not found.");
        }
    }
}
