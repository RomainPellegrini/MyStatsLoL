package db

import (
	"champion-api-go/models"
)

func SaveParticipants(matchID string, participants []models.ParticipantDTO) error {

	for _, p := range participants {

		_, err := DB.Exec(`
			INSERT INTO match_participantInfoChampion
			(match_id, puuid, champion_name, kills, deaths, assists, win)
			VALUES ($1, $2, $3, $4, $5, $6, $7)
		`,
			matchID,
			p.Puuid,
			p.ChampionName,
			p.Kills,
			p.Deaths,
			p.Assists,
			p.Win,
		)

		if err != nil {
			return err
		}
	}

	return nil
}

func SaveParticipantDetail(matchID string, p models.MatchParticipantDetailDTO) error {

	query := `
		INSERT INTO match_participantInfoChampion (
			match_id,
			puuid,
			riot_id_game_name,
			champion_name,
			champ_level,
			win,
			summoner1_id,
			summoner2_id,
			item0, item1, item2, item3, item4, item5, item6,
			kills,
			deaths,
			assists,
			total_minions_killed,
			total_damage_dealt_to_champions
		)
		VALUES (
			$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20
		)
	`

	_, err := DB.Exec(
		query,
		matchID,
		p.Puuid,
		p.RiotIdGameName,
		p.ChampionName,
		p.ChampLevel,
		p.Win,
		p.Summoner1Id,
		p.Summoner2Id,
		p.Items[0],
		p.Items[1],
		p.Items[2],
		p.Items[3],
		p.Items[4],
		p.Items[5],
		p.Items[6],
		p.Kills,
		p.Deaths,
		p.Assists,
		p.TotalMinionsKilled,
		p.TotalDamageDealtToChampions,
	)

	return err
}
