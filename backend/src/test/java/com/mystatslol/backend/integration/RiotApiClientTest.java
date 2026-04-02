package com.mystatslol.backend.integration;

import com.mystatslol.backend.riot.RiotApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RiotApiClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RiotApiClient riotApiClient;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        riotApiClient = new RiotApiClient(restTemplate);

        ReflectionTestUtils.setField(riotApiClient, "API_KEY", "test_key");
    }

    @Test
    void getAccountByPuuidTest() {

        Map<String, Object> response = new HashMap<>();
        response.put("gameName", "Romain");
        response.put("tagLine", "EUW");

        ResponseEntity<Map<String,Object>> entity =
                new ResponseEntity<>(response, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn((ResponseEntity) entity);

        Map<String,Object> result = riotApiClient.getAccountByPuuid("puuid1");

        assertEquals("Romain", result.get("gameName"));
        assertEquals("EUW", result.get("tagLine"));
    }

    @Test
    void getAccountByRiotIdTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("gameName", "Romain");
        response.put("tagLine", "EUW");

        ResponseEntity<Map<String,Object>> entity =
                new ResponseEntity<>(response, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn((ResponseEntity) entity);

        Map<String,Object> result = riotApiClient.getAccountByPuuid("puuid1");

        assertEquals("Romain", result.get("gameName"));
        assertEquals("EUW", result.get("tagLine"));

    }

}
