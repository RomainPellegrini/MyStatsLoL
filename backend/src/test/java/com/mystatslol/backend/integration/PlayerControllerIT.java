package com.mystatslol.backend.integration;


import com.mystatslol.backend.controller.PlayerController;
import com.mystatslol.backend.riot.RiotApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RiotApiClient riotApiClient;

    @Test
    void shouldGetPlayerByPuuid() throws Exception {

        Map<String,Object> apiResponse = new HashMap<>();
        apiResponse.put("gameName","Romain");
        apiResponse.put("tagLine","EUW");

        Mockito.when(riotApiClient.getAccountByPuuid("123"))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/players/playerByPuuid/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puuid").value("123"))
                .andExpect(jsonPath("$.gameName").value("Romain"))
                .andExpect(jsonPath("$.tagLine").value("EUW"));

    }
}