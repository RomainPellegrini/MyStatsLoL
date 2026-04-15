package kafka

import (
	"champion-api-go/models"
	"encoding/json"
	"fmt"
	"log"

	"github.com/IBM/sarama"
)

func StartConsumer() {
	config := sarama.NewConfig()
	config.Consumer.Return.Errors = true

	brokers := []string{"localhost:9092"}

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

	case "match":
		var match models.MatchSummaryDTO
		err := json.Unmarshal(msg, &match)
		if err != nil {
			log.Println("Erreur match:", err)
			break
		}
		fmt.Println("Match reçu :", match.MatchID)

	default:
		log.Println("Type inconnu :", string(msg))
	}
}
