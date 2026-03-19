package com.mystatslol.backend.DTO.riotresponse;

import java.util.List;

public record MatchRiotInfoDTO (
        long gameCreation,
        int gameDuration,
        String gameVersion,
        int queueId,
        String gameMode,
        String gameName,
        List<ParticipantRiotDTO> participants){

}
