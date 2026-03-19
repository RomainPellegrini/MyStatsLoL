package com.mystatslol.backend.DTO;

public record ParticipantDTO(

        String puuid,
        String riotIdGameName,
        String riotIdTagline,
        String championName,
        int championId,
        int champLevel,
        int teamId,
        String teamPosition,
        boolean win,
        int kills,
        int deaths,
        int assists
) {
}
