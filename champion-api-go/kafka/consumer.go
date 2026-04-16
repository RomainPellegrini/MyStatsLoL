package kafka

import (
	"champion-api-go/db"
	"champion-api-go/models"
	"encoding/json"
	"fmt"
	"log"
	"net"
	"time"

	"github.com/IBM/sarama"
)

func StartConsumer() {
	config := sarama.NewConfig()
	config.Consumer.Return.Errors = true

	brokers := []string{"kafka:9092"}

	consumer, err := sarama.NewConsumer(brokers, config)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("Kafka consumer started...")

	// 🔥 Lire toutes les partitions
	for i := 0; i < 3; i++ {
		pc, err := consumer.ConsumePartition("mystatslolTopic", int32(i), sarama.OffsetNewest)
		if err != nil {
			log.Fatal(err)
		}

		go func(pc sarama.PartitionConsumer, partition int32) {
			for {
				select {
				case msg := <-pc.Messages():
					fmt.Printf("Key: %s | Value: %s\n", string(msg.Key), string(msg.Value))
					SerializeData(string(msg.Key), msg.Value)
				case err := <-pc.Errors():
					log.Println("Kafka error:", err)
				}
			}
		}(pc, int32(i))
	}

}

func SerializeData(key string, msg []byte) {
	switch string(key) {

	case "player":
		var player models.PlayerDTO
		err := json.Unmarshal(msg, &player)
		if err != nil {
			log.Println("Erreur player:", err)
			break
		}
		fmt.Println("Player reçu :", player.GameName)
		err = db.SavePlayer(player)
		if err != nil {
			log.Println("DB error:", err)
		}

	case "match":
		var match models.MatchSummaryDTO
		err := json.Unmarshal(msg, &match)
		if err != nil {
			log.Println("Erreur match:", err)
			break
		}
		fmt.Println("Match reçu :", match.MatchID)

		err = db.SaveMatch(match)
		if err != nil {
			log.Println("Match DB error:", err)
			break
		}

		err = db.SaveParticipants(match.MatchID, match.Participants)
		if err != nil {
			log.Println("Participant DB error:", err)
		}

		err = db.SaveParticipantDetail(match.MatchID, *match.RequestingPlayer)
		if err != nil {
			log.Println("ParticipantDetail DB error:", err)
		}

	default:
		log.Println("Type inconnu :", string(msg))
	}
}

func WaitKafka(broker string) {
	for i := 0; i < 20; i++ {
		conn, err := net.DialTimeout("tcp", broker, 2*time.Second)
		if err == nil {
			conn.Close()
			return
		}
		log.Println("waiting Kafka...")
		time.Sleep(2 * time.Second)
	}
	log.Fatal("Kafka not ready")
}
