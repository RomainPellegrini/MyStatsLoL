package com.mystatslol.backend.controller;

import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PlayerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PlayerController {
    public static final String BASE_URI = "/players";

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/playerByPuuid/{puuid}")
    public PlayerDTO getPlayer(@PathVariable String puuid) {
        return playerService.getPlayerByPuuid(puuid);
    }
    @GetMapping("/playerByName/{gameName}/{tagLine}")
    public PlayerDTO getPlayer(   @PathVariable String gameName,
                                  @PathVariable String tagLine) {
        return playerService.getPlayerByName(gameName,tagLine);
    }
    @DeleteMapping("/deletePlayer/{puuid}")
    public ResponseEntity<String> deletePlayer(@PathVariable String puuid) {
        try {
            playerService.deletePlayerByPuuid(puuid);
            return ResponseEntity.ok("Player succesfully deleted.");
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
