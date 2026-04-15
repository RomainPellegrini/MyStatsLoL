package main

import (
	"net/http"

	"champion-api-go/kafka"

	"github.com/gin-gonic/gin"
)

func main() {
	InitDB()

	go kafka.StartConsumer()

	r := gin.Default()

	r.GET("/champions/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "test",
		})
	})

	r.Run(":8081")
}
