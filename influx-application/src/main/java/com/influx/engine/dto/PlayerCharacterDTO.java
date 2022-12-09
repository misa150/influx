package com.influx.engine.dto;

import com.influx.database.entity.enums.PlayerOnlineStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlayerCharacterDTO {

    private String playerName;
    private BattleAttributesDTO battleAttributes;
    private PlayerOnlineStatus playerOnlineStatus;
}
