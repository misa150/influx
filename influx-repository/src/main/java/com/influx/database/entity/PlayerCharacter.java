package com.influx.database.entity;


import com.influx.database.entity.enums.PlayerOnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "player_characters", schema = "world")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCharacter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String playerName;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "battle_attributes_id")
    private BattleAttributes battleAttributes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerOnlineStatus playerOnlineStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
}
