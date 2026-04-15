package kafka

import (
	"fmt"
	"log"

	"github.com/IBM/sarama"
)

type PlayerDTO struct {
	Puuid    string `json:"puuid"`
	GameName string `json:"gameName"`
	TagLine  string `json:"tagLine"`
}

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
					fmt.Printf("Partition %d → %s\n", partition, string(msg.Value))

				case err := <-pc.Errors():
					log.Println("Kafka error:", err)
				}
			}
		}(pc, int32(i))
	}
}
