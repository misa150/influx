package com.influx.engine.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDetails {

    private String logMessage;
}
