package com.influx.engine.util.mapper;


import com.influx.engine.dto.BattleAttributesDTO;
import com.influx.engine.entity.BattleAttributes;

public final class BattleAttributesMapper {

    public static BattleAttributesDTO map(BattleAttributes battleAttributes) {
        return BattleAttributesDTO.builder()
                .attackPower(battleAttributes.getAttackPower())
                .baseLevel(battleAttributes.getBaseLevel())
                .experience(battleAttributes.getExperience())
                .mana(battleAttributes.getMana())
                .hitPoints(battleAttributes.getHitPoints())
                .moveSpeed(battleAttributes.getMoveSpeed())
                .playerHealthStatus(battleAttributes.getPlayerHealthStatus())
                .build();
    }
}
