package com.influx.engine.util.literals.logs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorLiterals {

    public static final String ADD_PLAYER_EXISTING_ERROR = "Unable to save new player with name %s. Player Name is already existing";
    public static final String UPDATE_PLAYER_NOT_EXISTING_ERROR = "Unable to update player with name %s. Player is not existing";
    public static final String DELETE_PLAYER_NOT_EXISTING_ERROR = "Unable to delete player with name %s. Player is not existing";
}
