package com.mystatslol.backend.service;

import com.mystatslol.backend.DTO.MatchSummaryDTO;
import com.mystatslol.backend.DTO.PlayerDTO;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public boolean isKafkaAvailable() {
        try {
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");

            try (AdminClient client = AdminClient.create(props)) {
                client.listTopics().names().get(1, TimeUnit.SECONDS);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public void sendPlayer(PlayerDTO player) {

        if (!isKafkaAvailable()) {
            System.out.println("Kafka down → skip send");
            return;
        }

        kafkaTemplate.send("mystatslolTopic", "player", player);
    }
    public void sendMatch(MatchSummaryDTO match) {

        if (!isKafkaAvailable()) {
            System.out.println("Kafka down → skip send");
            return;
        }

            kafkaTemplate.send("mystatslolTopic", "match", match);


    }
}
