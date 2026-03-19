package com.mystatslol.backend.riot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RiotApiClient {

    @Value("${riot.api.key}")
    private String API_KEY;
    private static final Logger log = LoggerFactory.getLogger(RiotApiClient.class);

    private final RestTemplate restTemplate;

    private final String BASE_URL = "https://europe.api.riotgames.com";

    public RiotApiClient() {
        this.restTemplate = new RestTemplate();
    }

    private <T> T get(String url, Class<T> responseType) {
        log.debug("GET {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", API_KEY);

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                responseType
        );

        return response.getBody();
    }


    private <T> T get(String url, ParameterizedTypeReference<T> responseType) {
        log.debug("GET {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", API_KEY);

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                responseType
        );

        return response.getBody();
    }


    public Map<String, Object> getAccountByPuuid(String puuid) {
        String url = BASE_URL
                + "/riot/account/v1/accounts/by-puuid/" + puuid;
        return get(url, new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> getAccountByRiotId(String gameName, String tagLine) {
        String url = BASE_URL
                + "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine;
        return get(url, new ParameterizedTypeReference<Map<String, Object>>() {});
    }


    public List<String> getLastMatchsIds(String puuid, int count) {
        String url = BASE_URL
                + "/lol/match/v5/matches/by-puuid/" + puuid
                + "/ids?start=0&count=" + count;
        return get(url, new ParameterizedTypeReference<List<String>>() {});
    }

    public List<String> getStartMatchsIds(String puuid,int start, int count) {
        String url = BASE_URL
                + "/lol/match/v5/matches/by-puuid/" + puuid
                + "/ids?start="+start+"&count=" + count;
        return get(url, new ParameterizedTypeReference<List<String>>() {});
    }

    public Map<String, Object> getMatchById(String matchId) {
        String url = BASE_URL
                + "/lol/match/v5/matches/" + matchId;
        return get(url, new ParameterizedTypeReference<Map<String, Object>>() {});
    }

}
