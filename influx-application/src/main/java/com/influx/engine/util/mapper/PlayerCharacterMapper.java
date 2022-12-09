package com.influx.engine.util.mapper;


import com.influx.engine.dto.PlayerCharacterDTO;
import com.influx.engine.entity.PlayerCharacter;

public final class PlayerCharacterMapper {

    public static PlayerCharacterDTO map(PlayerCharacter playerCharacter) {
        return PlayerCharacterDTO.builder()
                .playerName(playerCharacter.getPlayerName())
                .battleAttributes(BattleAttributesMapper.map(playerCharacter.getBattleAttributes()))
                .playerOnlineStatus(playerCharacter.getPlayerOnlineStatus())
                .build();
    }

}
