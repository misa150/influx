package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.influx.engine.util.literals.basevalues.BaseValuesLiterals.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BattleAttributeService {

    public void updateBattleAttributes(PlayerCharacter playerCharacter, AddPlayerCharacterDTO updatePlayer) {
        var battleAttributes = playerCharacter.getBattleAttributes();

        battleAttributes.setBaseLevel(updatePlayer.getBattleAttributes().getBaseLevel());
        battleAttributes.setExperience(updatePlayer.getBattleAttributes().getExperience());

        battleAttributes.setHitPoints(updatePlayer.getBattleAttributes().getHitPoints());
        battleAttributes.setMana(updatePlayer.getBattleAttributes().getMana());

        battleAttributes.setAttackPower(updatePlayer.getBattleAttributes().getAttackPower());
        battleAttributes.setMoveSpeed(updatePlayer.getBattleAttributes().getMoveSpeed());
    }

    public BattleAttributes initializeBattleAttributes(AddPlayerCharacterDTO addNewPlayer) {
        return BattleAttributes
                .builder()
                .hitPoints(addNewPlayer.getBattleAttributes().getHitPoints())
                .mana(addNewPlayer.getBattleAttributes().getMana())
                .experience(BASE_EXP)
                .baseLevel(BASE_LEVEL)
                .attackPower(addNewPlayer.getBattleAttributes().getAttackPower())
                .moveSpeed(addNewPlayer.getBattleAttributes().getMoveSpeed())
                .playerHealthStatus(INITIAL_PLAYER_HEALTH_STATUS)
                .build();
    }
}
