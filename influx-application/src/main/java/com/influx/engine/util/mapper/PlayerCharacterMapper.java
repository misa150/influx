package com.influx.engine.util.mapper;


import com.influx.database.entity.PlayerCharacter;
import com.influx.engine.dto.playercharacter.PlayerCharacterDTO;

public final class PlayerCharacterMapper {

    private PlayerCharacterMapper () {}

    public static PlayerCharacterDTO map(PlayerCharacter playerCharacter) {
        return PlayerCharacterDTO.builder()
                .playerName(playerCharacter.getPlayerName())
                .playerDisplayName(playerCharacter.getPlayerDisplayName())
                .battleAttributes(BattleAttributesMapper.map(playerCharacter.getBattleAttributes()))
                .playerOnlineStatus(playerCharacter.getPlayerOnlineStatus())
                .creationDate(playerCharacter.getCreationDate())
                .lastUpdatedDate(playerCharacter.getLastUpdatedDate())
                .build();
    }

}
