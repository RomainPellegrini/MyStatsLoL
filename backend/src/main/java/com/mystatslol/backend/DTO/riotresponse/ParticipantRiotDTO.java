package com.mystatslol.backend.DTO.riotresponse;

public record ParticipantRiotDTO(
        String puuid,
        String summonerName,
        String riotIdGameName,
        String riotIdTagline,

        int championId,
        String championName,
        int champLevel,

        int teamId,
        String teamPosition,
        String individualPosition,
        String lane,
        String role,

        boolean win,

        int summoner1Id,
        int summoner2Id,

        // KDA
        int kills,
        int deaths,
        int assists,
        int largestKillingSpree,
        int largestMultiKill,
        boolean firstBloodKill,

        // Économie
        int goldEarned,
        int goldSpent,

        // Dégâts
        int totalDamageDealtToChampions,
        int physicalDamageDealtToChampions,
        int magicDamageDealtToChampions,
        int trueDamageDealtToChampions,
        int totalDamageTaken,
        int damageSelfMitigated,

        // Items
        int item0, int item1, int item2,
        int item3, int item4, int item5, int item6,

        int totalMinionsKilled){


}
