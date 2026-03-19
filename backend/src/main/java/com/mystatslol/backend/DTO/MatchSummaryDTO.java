package com.mystatslol.backend.DTO;

import java.util.List;

public record MatchSummaryDTO(
        String matchId,
        long gameCreation,
        int gameDuration,
        String gameMode,
        List<ParticipantDTO> participants,
        MatchParticipantDetailDTO requestingPlayer) {

}
