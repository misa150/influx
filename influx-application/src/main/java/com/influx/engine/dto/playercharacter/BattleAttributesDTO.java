package com.influx.engine.dto.playercharacter;

import com.influx.database.entity.enums.PlayerHealthStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class BattleAttributesDTO {

    private Long experience;
    private BigDecimal hitPoints;
    private BigDecimal mana;
    private Integer moveSpeed;
    private Integer attackPower;
    private Integer baseLevel;
    private PlayerHealthStatus playerHealthStatus;
}
