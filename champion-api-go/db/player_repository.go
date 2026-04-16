package db

import (
	"champion-api-go/models"
	"database/sql"
)

var DB *sql.DB

func SavePlayer(player models.PlayerDTO) error {
	query := `
		INSERT INTO Player (puuid, game_name, tag_line)
		VALUES ($1, $2, $3)
		ON CONFLICT (puuid) DO UPDATE
		SET game_name = EXCLUDED.game_name,
		    tag_line = EXCLUDED.tag_line
	`

	_, err := DB.Exec(query,
		player.Puuid,
		player.GameName,
		player.TagLine,
	)

	return err
}
