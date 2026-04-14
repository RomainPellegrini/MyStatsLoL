package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	InitDB()

	r := gin.Default()

	r.GET("/champions/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "test",
		})
	})

	r.Run(":8081")
}
