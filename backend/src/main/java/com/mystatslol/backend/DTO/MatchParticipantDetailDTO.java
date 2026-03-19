package com.mystatslol.backend.DTO;

public record MatchParticipantDetailDTO(
        String puuid,
        String riotIdGameName,
        String championName,
        int champLevel,
        boolean win,

        int summoner1Id,
        int summoner2Id,

        int[] items,

        int kills,
        int deaths,
        int assists,
       // double kdaRatio,

        int totalMinionsKilled,
      //  double csPerMinute,

        // Dégâts
        int totalDamageDealtToChampions
/*
        // Vision
        int visionScore,
        int controlWardsPlaced,

        // Runes
        int keystoneRuneId,
        int primaryStyle,
        int subStyle
        */
)
{
}
