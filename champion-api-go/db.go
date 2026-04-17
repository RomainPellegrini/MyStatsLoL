package main

import (
	"champion-api-go/db"
	"database/sql"
	"fmt"
	"log"
	"os"

	"github.com/joho/godotenv"
	_ "github.com/lib/pq"
)

var DB *sql.DB

func InitDB() {

	env := os.Getenv("APP_ENV")

	if env != "docker" {
		err := godotenv.Load("../.env")
		if err != nil {
			log.Println("⚠️ .env non chargé (normal si en docker)")
		}
	}

	// Récupérer les variables
	user := os.Getenv("POSTGRES_USER")
	password := os.Getenv("POSTGRES_PASSWORD")
	dbname := os.Getenv("POSTGRES_DB")
	host := os.Getenv("POSTGRES_HOST")
	if host == "" {
		host = "localhost"
	}
	port := "5432"

	// Construire la string de connexion
	connStr := fmt.Sprintf(
		"user=%s password=%s dbname=%s host=%s port=%s sslmode=disable",
		user, password, dbname, host, port,
	)

	var errDB error
	DB, errDB = sql.Open("postgres", connStr)
	if errDB != nil {
		log.Fatal(errDB)
	}

	errDB = DB.Ping()
	if errDB != nil {
		log.Fatal(errDB)
	}

	fmt.Println("✅ Connected to PostgreSQL")

	db.DB = DB
}
