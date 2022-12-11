package com.influx.engine.dto.addplayer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlayerCharacterDTO {

    private String playerName;
    private String playerDisplayName;
    private UpdateBattleAttributesDTO battleAttributes;
}
