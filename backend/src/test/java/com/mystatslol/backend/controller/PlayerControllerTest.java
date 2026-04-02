package com.mystatslol.backend.controller;


import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @Test
    void getPlayerPuuidTest() throws Exception {

        PlayerDTO player = new PlayerDTO("puuid1", "Romain", "EUW");

        when(playerService.getPlayerByPuuid("puuid1")).thenReturn(player);

        mockMvc.perform(get("/players/playerByPuuid/puuid1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puuid").value("puuid1"))
                .andExpect(jsonPath("$.gameName").value("Romain"))
                .andExpect(jsonPath("$.tagLine").value("EUW"));

        verify(playerService).getPlayerByPuuid("puuid1");
    }

    @Test
    void testGetPlayerNameTest() throws Exception {

        PlayerDTO player = new PlayerDTO("puuid1", "Romain", "EUW");

        when(playerService.getPlayerByName("Romain", "EUW")).thenReturn(player);

        mockMvc.perform(get("/players/playerByName/Romain/EUW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puuid").value("puuid1"))
                .andExpect(jsonPath("$.gameName").value("Romain"))
                .andExpect(jsonPath("$.tagLine").value("EUW"));

        verify(playerService).getPlayerByName("Romain", "EUW");
    }

    @Test
    void deletePlayerTest() throws Exception {
        doNothing().when(playerService).deletePlayerByPuuid("puuid1");

        mockMvc.perform(delete("/players/deletePlayer/puuid1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player succesfully deleted."));

        verify(playerService).deletePlayerByPuuid("puuid1");
    }
}