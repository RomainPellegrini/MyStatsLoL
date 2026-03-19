package com.mystatslol.backend.DTO.riotresponse;

import java.util.List;

public record MatchRiotMetaDTO(
        String matchId,
        List<String> participants
) {
}
