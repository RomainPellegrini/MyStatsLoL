package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.entity.Player;
import com.mystatslol.backend.repository.PlayerRepository;
import com.mystatslol.backend.riot.RiotApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private RiotApiClient riotApiClient;
    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayerByPuuid() {
        String puuid = "puuid1";

        Map<String, Object> riotResponse = new HashMap<>();
        riotResponse.put("puuid", "puuid1");
        riotResponse.put("gameName", "Romain");
        riotResponse.put("tagLine", "EUW");

        when(riotApiClient.getAccountByPuuid(puuid)).thenReturn(riotResponse);
        PlayerDTO result = playerService.getPlayerByPuuid(puuid);

        assertEquals("puuid1", result.puuid());
        assertEquals("Romain", result.gameName());
        assertEquals("EUW", result.tagLine());

        verify(playerRepository, times(1)).save(any(Player.class));
        verify(kafkaProducerService, times(1)).sendPlayer(any(PlayerDTO.class));
    }

    @Test
    void getPlayerByName() {
        String name = "Romain";
        String tagLine = "EUW";
        Map<String, Object> riotResponse = new HashMap<>();
        riotResponse.put("puuid", "puuid1");
        riotResponse.put("gameName", "Romain");
        riotResponse.put("tagLine", "EUW");

        when(riotApiClient.getAccountByRiotId(name,tagLine)).thenReturn(riotResponse);
        PlayerDTO result = playerService.getPlayerByName(name,tagLine);

        assertEquals("puuid1", result.puuid());
        assertEquals("Romain", result.gameName());
        assertEquals("EUW", result.tagLine());

        verify(playerRepository, times(1)).save(any(Player.class));
        verify(kafkaProducerService, times(1)).sendPlayer(any(PlayerDTO.class));
    }

    @Test
    void deletePlayerByPuuid() {
        String puuid = "puuid123";

        when(playerRepository.existsById(puuid)).thenReturn(true);

        playerService.deletePlayerByPuuid(puuid);

        verify(playerRepository).deleteById(puuid);
    }
    @Test
    void ThrowExceptionWhenPlayerNotFoundTest() {

        String puuid = "puuid123";

        when(playerRepository.existsById(puuid)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> playerService.deletePlayerByPuuid(puuid)
        );

        assertTrue(exception.getMessage().contains("not found"));
    }
}