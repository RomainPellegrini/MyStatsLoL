package com.mystatslol.backend.DTO;

import com.mystatslol.backend.DTO.riotresponse.MatchRiotDTO;
import com.mystatslol.backend.DTO.riotresponse.ParticipantRiotDTO;

import java.util.ArrayList;
import java.util.List;

public record MatchSummaryDTO(
        String matchId,
        long gameCreation,
        int gameDuration,
        String gameMode,
        List<ParticipantDTO> participants,
        MatchParticipantDetailDTO requestingPlayer) {
    public static MatchSummaryDTO from(MatchRiotDTO matchRiotDTO, String puuid){
        String gameMode ="";
        switch (matchRiotDTO.info().queueId()){
            case 400 :
                gameMode = "Draft";
                break;
            case 420 :
                gameMode= "SoloQ";
                break;
            case 430 :
                gameMode= "Blind";
                break;
            case 440 :
                gameMode= "Flex";
                break;
            default:
                gameMode="other";
                break;
        }
        List<ParticipantDTO> participantDTOS = new ArrayList<>();
        MatchParticipantDetailDTO matchParticipantDetailDTO = null;
        for ( ParticipantRiotDTO p : matchRiotDTO.info().participants() ){
            participantDTOS.add(ParticipantDTO.from(p));
            if(p.puuid().equals(puuid)){
                matchParticipantDetailDTO = MatchParticipantDetailDTO.from(p);
            }
        }

        MatchSummaryDTO matchSummaryDTO = new MatchSummaryDTO(
                matchRiotDTO.metadata().matchId(),
                matchRiotDTO.info().gameCreation(),
                matchRiotDTO.info().gameDuration(),
                gameMode,
                participantDTOS,
                matchParticipantDetailDTO

        );

        return matchSummaryDTO;
    }
}
