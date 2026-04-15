package models

type MatchParticipantDetailDTO struct {
	Puuid        string `json:"puuid"`
	ChampionName string `json:"championName"`
	Kills        int    `json:"kills"`
	Deaths       int    `json:"deaths"`
	Assists      int    `json:"assists"`
	GoldEarned   int    `json:"goldEarned"`
	Win          bool   `json:"win"`
}
