package models

type MatchParticipantDetailDTO struct {
	Puuid          string `json:"puuid"`
	RiotIdGameName string `json:"riotIdGameName"`
	ChampionName   string `json:"championName"`
	ChampLevel     int    `json:"champLevel"`
	Win            bool   `json:"win"`

	Summoner1Id int `json:"summoner1Id"`
	Summoner2Id int `json:"summoner2Id"`

	Items []int `json:"items"`

	Kills   int `json:"kills"`
	Deaths  int `json:"deaths"`
	Assists int `json:"assists"`

	TotalMinionsKilled int `json:"totalMinionsKilled"`

	TotalDamageDealtToChampions int `json:"totalDamageDealtToChampions"`
}
