package com.influx.engine.service;

import com.influx.database.entity.PlayerCharacter;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
