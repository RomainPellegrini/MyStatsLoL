package com.mystatslol.backend.repository;

import com.mystatslol.backend.entity.Match;
import com.mystatslol.backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, String> {
    boolean existsByMatchId(String matchId);
}
