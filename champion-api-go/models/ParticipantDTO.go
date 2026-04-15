package models

type ParticipantDTO struct {
	Puuid        string `json:"puuid"`
	SummonerName string `json:"summonerName"`
	ChampionName string `json:"championName"`
	Kills        int    `json:"kills"`
	Deaths       int    `json:"deaths"`
	Assists      int    `json:"assists"`
	Win          bool   `json:"win"`
}
