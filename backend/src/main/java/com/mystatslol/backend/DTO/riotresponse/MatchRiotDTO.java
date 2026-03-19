package com.mystatslol.backend.DTO.riotresponse;

public record MatchRiotDTO(
        MatchRiotMetaDTO metadata,
        MatchRiotInfoDTO info
) {
}
