package models

type MatchSummaryDTO struct {
	MatchID          string                     `json:"matchId"`
	GameCreation     int64                      `json:"gameCreation"`
	GameDuration     int                        `json:"gameDuration"`
	GameMode         string                     `json:"gameMode"`
	Participants     []ParticipantDTO           `json:"participants"`
	RequestingPlayer *MatchParticipantDetailDTO `json:"requestingPlayer"`
}
