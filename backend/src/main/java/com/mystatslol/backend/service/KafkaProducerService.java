package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.MatchSummaryDTO;
import com.mystatslol.backend.DTO.PlayerDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPlayer(PlayerDTO player) {
        kafkaTemplate.send("mystatslolTopic", "player", player);
    }
    public void sendMatch(MatchSummaryDTO match) {
        kafkaTemplate.send("mystatslolTopic", "match", match);

    }
}
