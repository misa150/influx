package com.influx.engine.util.literals.basevalues;

import com.influx.database.entity.enums.PlayerHealthStatus;
import com.influx.database.entity.enums.PlayerOnlineStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseValuesLiterals {

    public static final Long BASE_EXP = 0L;
    public static final Integer BASE_LEVEL = 1;
    public static final PlayerHealthStatus INITIAL_PLAYER_HEALTH_STATUS = PlayerHealthStatus.ALIVE;
    public static final PlayerOnlineStatus INITIAL_PLAYER_ONLINE_STATUS =  PlayerOnlineStatus.OFFLINE;

    public static final String FIND_ALL_SORT = "playerName";


}
