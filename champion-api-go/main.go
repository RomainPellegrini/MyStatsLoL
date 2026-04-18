package main

import (
	"net/http"
	"os"

	"champion-api-go/kafka"

	"github.com/gin-gonic/gin"
)

func main() {
	InitDB()
	env := os.Getenv("APP_ENV")
	if env == "docker" {
		go kafka.WaitKafka(os.Getenv("KAFKA_BROKER"))
	}
	go kafka.StartConsumer()

	r := gin.Default()

	r.GET("/champions/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "test",
		})
	})

	r.Run(":8081")
}
