package com.influx.database.entity;


import com.influx.database.entity.enums.PlayerHealthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "battle_attributes", schema = "world")
@NoArgsConstructor
@AllArgsConstructor
public class BattleAttributes {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Long experience;

    private BigDecimal hitPoints;

    private BigDecimal mana;

    private Integer moveSpeed;

    private Integer attackPower;

    private Integer baseLevel;

    @Enumerated(EnumType.STRING)
    private PlayerHealthStatus playerHealthStatus;

}
