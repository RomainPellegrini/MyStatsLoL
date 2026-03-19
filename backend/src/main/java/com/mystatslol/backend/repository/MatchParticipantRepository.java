package com.mystatslol.backend.repository;

import com.mystatslol.backend.entity.MatchParticipant;
import com.mystatslol.backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchParticipantRepository extends JpaRepository<MatchParticipant, String> {
    List<MatchParticipant> findByMatch_MatchId(String matchId);
    Optional<MatchParticipant> findByMatch_MatchIdAndPuuid(String matchId, String puuid);
}
