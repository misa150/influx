package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.dto.addplayer.UpdateBattleAttributesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.influx.engine.util.literals.basevalues.BaseValuesLiterals.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleAttributesServiceTest extends PlayerCharacterLiterals {

    private BattleAttributeService cut;

    @BeforeEach
    private void setup() {
        cut = new BattleAttributeService();
    }

    @Test
    void updateBattleAttributesShouldReturnOk() {
        var playerCharacter = createPlayerCharacter();

        cut.updateBattleAttributes(playerCharacter, createAddPlayerCharacterDTO());

        var battleAttributes = playerCharacter.getBattleAttributes();
        assertEquals(LEVEL, battleAttributes.getBaseLevel());
        assertEquals(EXP, battleAttributes.getExperience());
        assertEquals(HIT_POINTS, battleAttributes.getHitPoints());
        assertEquals(MANA, battleAttributes.getMana());
        assertEquals(ATTACK_POWER, battleAttributes.getAttackPower());
        assertEquals(MOVE_SPEED, battleAttributes.getMoveSpeed());
    }

    @Test
    void initializeBattleAttributesShouldHaveCorrectValues() {
        var response = cut.initializeBattleAttributes(createAddPlayerCharacterDTO());

        assertEquals(BASE_LEVEL, response.getBaseLevel());
        assertEquals(BASE_EXP, response.getExperience());
        assertEquals(HIT_POINTS, response.getHitPoints());
        assertEquals(MANA, response.getMana());
        assertEquals(ATTACK_POWER, response.getAttackPower());
        assertEquals(MOVE_SPEED, response.getMoveSpeed());
        assertEquals(PLAYER_HEALTH_STATUS.ALIVE, INITIAL_PLAYER_HEALTH_STATUS);
    }

    private AddPlayerCharacterDTO createAddPlayerCharacterDTO() {
        var battleAttributes = new UpdateBattleAttributesDTO();

        battleAttributes.setBaseLevel(LEVEL);
        battleAttributes.setExperience(EXP);
        battleAttributes.setHitPoints(HIT_POINTS);
        battleAttributes.setMana(MANA);
        battleAttributes.setAttackPower(ATTACK_POWER);
        battleAttributes.setMoveSpeed(MOVE_SPEED);

        var addPlayerCharacter = new AddPlayerCharacterDTO();

        addPlayerCharacter.setPlayerName(PLAYER_CHARACTER_NAME);
        addPlayerCharacter.setPlayerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME);
        addPlayerCharacter.setBattleAttributes(battleAttributes);

        return addPlayerCharacter;
    }
    private PlayerCharacter createPlayerCharacter() {
        return PlayerCharacter
                .builder()
                .battleAttributes(BattleAttributes
                        .builder()
                        .baseLevel(1)
                        .experience(0L)
                        .hitPoints(BigDecimal.ONE)
                        .mana(BigDecimal.ONE)
                        .attackPower(1)
                        .moveSpeed(1)
                        .build())
                .build();
    }

}
