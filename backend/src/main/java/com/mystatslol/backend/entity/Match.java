package com.mystatslol.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Match {

    @Id
    private String matchId;

    private long gameCreation;
    private int gameDuration;
    private int queueId;
}