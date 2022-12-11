package com.influx.engine.dto.playercharacter;

import com.influx.database.entity.enums.PlayerOnlineStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PlayerCharacterDTO {

    private String playerName;
    private String playerDisplayName;
    private BattleAttributesDTO battleAttributes;
    private PlayerOnlineStatus playerOnlineStatus;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedDate;
}
