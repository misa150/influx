package com.influx.engine.service;

import com.influx.database.entity.enums.PlayerHealthStatus;
import com.influx.database.entity.enums.PlayerOnlineStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlayerCharacterLiterals {

    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();

    protected static final String PLAYER_CHARACTER_NAME = "player_character_name";
    protected static final String PLAYER_CHARACTER_DISPLAY_NAME = "player_character_display_name";

    protected static final PlayerOnlineStatus PLAYER_ONLINE_STATUS = PlayerOnlineStatus.ONLINE;

    protected static final LocalDateTime CREATION_DATE = CURRENT_TIME;
    protected static final LocalDateTime LAST_UPDATED_DATE = CURRENT_TIME.plusDays(1);

    protected static final Integer LEVEL = 2;
    protected static final Long EXP = 1L;

    protected static final BigDecimal HIT_POINTS = BigDecimal.valueOf(10);
    protected static final BigDecimal MANA = BigDecimal.valueOf(5);

    protected static final Integer ATTACK_POWER = 10;
    protected static final Integer MOVE_SPEED = 2;

    protected static final PlayerHealthStatus PLAYER_HEALTH_STATUS = PlayerHealthStatus.ALIVE;

    protected static final Integer PAGEABLE_LIMIT = 10;
    protected static final Integer PAGEABLE_OFFSET = 0;


}
