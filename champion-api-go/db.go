package main

import (
	"champion-api-go/db"
	"database/sql"
	"fmt"
	"log"
	"os"

	_ "github.com/lib/pq"
)

var DB *sql.DB

func InitDB() {
	// Charger le .env (chemin relatif)
	/*err := godotenv.Load("../.env")
	if err != nil {
		log.Fatal("Erreur chargement .env")
	}*/

	// Récupérer les variables
	user := os.Getenv("POSTGRES_USER")
	password := os.Getenv("POSTGRES_PASSWORD")
	dbname := os.Getenv("POSTGRES_DB")
	host := "postgres"
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
