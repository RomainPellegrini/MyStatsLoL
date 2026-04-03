package com.mystatslol.backend.DTO;

import com.mystatslol.backend.DTO.riotresponse.ParticipantRiotDTO;

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
    public static MatchParticipantDetailDTO from(ParticipantRiotDTO p) {
        return new MatchParticipantDetailDTO(
                p.puuid(),
                p.riotIdGameName(),
                p.championName(),
                p.champLevel(),
                p.win(),
                p.summoner1Id(),
                p.summoner2Id(),
                new int[]{p.item0(), p.item1(), p.item2(), p.item3(), p.item4(), p.item5(), p.item6()},
                p.kills(),
                p.deaths(),
                p.assists(),
                p.totalMinionsKilled(),
                p.totalDamageDealtToChampions()
        );
    }
}
