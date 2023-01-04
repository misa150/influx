package com.influx.engine.exceptions;

import com.influx.engine.service.logs.LogsService;

public class PlayerCharacterException extends RuntimeException {

    private LogsService logsService;

    public PlayerCharacterException (String errorMessage, LogsService logsService) {
        super(errorMessage);
        this.logsService = logsService;
        this.logsService.saveNewErrorLog(errorMessage);
    }

}
