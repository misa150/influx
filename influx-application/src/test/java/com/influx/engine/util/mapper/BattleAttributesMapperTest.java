package com.influx.engine.util.mapper;


import com.influx.database.entity.BattleAttributes;
import com.influx.engine.dto.playercharacter.BattleAttributesDTO;
import com.influx.engine.service.PlayerCharacterLiterals;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleAttributesMapperTest extends PlayerCharacterLiterals {

    @Test
    void mapShouldBeEqual() {
        var actual = BattleAttributesMapper.map(createBattleAttributes());
        var expected = createBattleAttributesDTO();

        assertEquals(expected, actual);
    }

    private BattleAttributesDTO createBattleAttributesDTO() {
        return BattleAttributesDTO
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
