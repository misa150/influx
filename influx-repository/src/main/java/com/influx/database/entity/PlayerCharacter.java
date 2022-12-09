package com.influx.database.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "player_characters", schema = "world")
public class PlayerCharacter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String playerName;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "battle_attributes_id")
    private BattleAttributes battleAttributes;
}
