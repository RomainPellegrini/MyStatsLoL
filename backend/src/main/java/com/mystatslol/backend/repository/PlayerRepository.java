package com.mystatslol.backend.repository;

import com.mystatslol.backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
