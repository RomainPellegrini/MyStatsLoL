package com.mystatslol.backend.controller;

import com.mystatslol.backend.DTO.MatchSummaryDTO;
import com.mystatslol.backend.DTO.PlayerDTO;
import com.mystatslol.backend.service.MatchService;
import com.mystatslol.backend.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = MatchController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MatchController {

    public static final String BASE_URI = "/match";

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/lastGame/{puuid}")
    public MatchSummaryDTO getLastGame(@PathVariable String puuid) {

        return matchService.getLastMatch(puuid);
    }

    @GetMapping("/last10Games/{puuid}")
    public List<MatchSummaryDTO> getLastGames(@PathVariable String puuid) {
        return matchService.getLast10Matches(puuid);
    }
    @GetMapping("/catchUpGames/{puuid}")
    public List<MatchSummaryDTO> catchUpGames(@PathVariable String puuid) {
        return matchService.catchUpMatches(puuid);
    }


}
