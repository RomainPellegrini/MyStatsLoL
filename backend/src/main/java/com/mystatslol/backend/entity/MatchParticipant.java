package com.mystatslol.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MatchParticipant {

    @Id
    @GeneratedValue
    private Long id;

    private String matchId;
    private String puuid;

    private int kills;
    private int deaths;
    private int assists;

    private int goldEarned;
    private int totalDamageDealtToChampions;
}