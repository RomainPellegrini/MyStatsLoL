package com.mystatslol.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Match {

    @Id
    private String matchId;

    private long gameCreation;
    private int gameDuration;
    private String gameVersion;
    private int queueId;
    private String gameMode;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchParticipant> participants;
}