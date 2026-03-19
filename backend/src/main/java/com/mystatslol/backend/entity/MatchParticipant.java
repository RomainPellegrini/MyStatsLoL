package com.mystatslol.backend.entity;

import jakarta.persistence.*;

@Entity
public class MatchParticipant {

    @Id
    @GeneratedValue
    private Long id;


    private String puuid;
    private String riotIdGameName;
    private String riotIdTagline;


    private int championId;
    private String championName;
    private int champLevel;

    private int teamId; //100 ou 200
    private String teamPosition;
    private boolean win;

    private int kills;
    private int deaths;
    private int assists;

    private int goldEarned;
    private int totalDamageDealtToChampions;

    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;

    private int summoner1Id;
    private int summoner2Id;

    private int totalMinionsKilled;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
}