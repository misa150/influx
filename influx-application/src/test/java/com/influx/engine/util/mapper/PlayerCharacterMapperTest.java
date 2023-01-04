package com.influx.engine.util.mapper;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.engine.dto.playercharacter.PlayerCharacterDTO;
import com.influx.engine.service.PlayerCharacterLiterals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerCharacterMapperTest extends PlayerCharacterLiterals {

    @Test
    void mapShouldBeEqual() {

        var actual = PlayerCharacterMapper.map(createPlayerCharacter());
        var expected = createPlayerCharacterDTO();

        assertEquals(expected, actual);
    }

    private PlayerCharacter createPlayerCharacter() {
        return PlayerCharacter.builder()
                .playerName(PLAYER_CHARACTER_NAME)
                .playerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME)
                .battleAttributes(createBattleAttributes())
                .playerOnlineStatus(PLAYER_ONLINE_STATUS)
                .creationDate(CREATION_DATE)
                .lastUpdatedDate(LAST_UPDATED_DATE)
                .build();
    }

    private PlayerCharacterDTO createPlayerCharacterDTO() {
        return PlayerCharacterDTO.builder()
                .playerName(PLAYER_CHARACTER_NAME)
                .playerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME)
                .battleAttributes(BattleAttributesMapper.map(createBattleAttributes()))
                .playerOnlineStatus(PLAYER_ONLINE_STATUS)
                .creationDate(CREATION_DATE)
                .lastUpdatedDate(LAST_UPDATED_DATE)
                .build();
    }

    private BattleAttributes createBattleAttributes() {
        return BattleAttributes
                .builder()
                .baseLevel(LEVEL)
                .experience(EXP)
                .hitPoints(HIT_POINTS)
                .mana(MANA)
                .attackPower(ATTACK_POWER)
                .moveSpeed(MOVE_SPEED)
                .playerHealthStatus(PLAYER_HEALTH_STATUS)
                .build();
    }
}
