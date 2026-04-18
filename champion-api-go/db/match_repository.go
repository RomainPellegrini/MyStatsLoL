package db

import "champion-api-go/models"

func SaveMatch(match models.MatchSummaryDTO) error {

	// 🔥 insert match
	_, err := DB.Exec(`
		INSERT INTO MatchInfoChampion (match_id, game_creation, game_duration, game_mode)
		VALUES ($1, $2, $3, $4)
		ON CONFLICT (match_id) DO NOTHING
	`,
		match.MatchID,
		match.GameCreation,
		match.GameDuration,
		match.GameMode,
	)

	return err
}
