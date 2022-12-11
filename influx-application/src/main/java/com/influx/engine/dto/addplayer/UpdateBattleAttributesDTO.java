package com.influx.engine.dto.addplayer;

import com.influx.database.entity.enums.PlayerHealthStatus;
import lombok.Builder;
import lombok.Data;
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
