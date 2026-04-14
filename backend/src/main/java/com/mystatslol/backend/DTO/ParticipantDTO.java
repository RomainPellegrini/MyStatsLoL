package com.mystatslol.backend.DTO;

import com.mystatslol.backend.DTO.riotresponse.ParticipantRiotDTO;

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
    public static ParticipantDTO from(ParticipantRiotDTO participantRiotDTO){
        ParticipantDTO participantDTO = new ParticipantDTO(
                participantRiotDTO.puuid(),
                participantRiotDTO.riotIdGameName(),
                participantRiotDTO.riotIdTagline(),
                participantRiotDTO.championName(),
                participantRiotDTO.championId(),
                participantRiotDTO.champLevel(),
                participantRiotDTO.teamId(),
                participantRiotDTO.teamPosition(),
                participantRiotDTO.win(),
                participantRiotDTO.kills(),
                participantRiotDTO.deaths(),
                participantRiotDTO.assists()
        );
        return participantDTO;
    }
}
