package com.influx.engine.dto.addplayer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBattleAttributesDTO {

    private Long experience;
    private BigDecimal hitPoints;
    private BigDecimal mana;
    private Integer moveSpeed;
    private Integer attackPower;
    private Integer baseLevel;
}
