package com.influx.engine.service.logs;

import com.influx.engine.models.LogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogsService {

    private final ErrorLogService errorLogService;

    public void saveNewErrorLog(String errorMessage) {
        errorLogService.saveNewErrorLog(createLogDetails(errorMessage));
    }

    public LogDetails createLogDetails(String errorMessage) {
        return LogDetails.builder()
                .logMessage(errorMessage)
                .build();
    }
}
