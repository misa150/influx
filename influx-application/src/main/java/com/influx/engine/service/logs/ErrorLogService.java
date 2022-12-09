package com.influx.engine.service.logs;


import com.influx.database.entity.ErrorLog;
import com.influx.database.repository.ErrorLogRepository;
import com.influx.engine.models.LogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    public void saveNewErrorLog (LogDetails logDetails) {
        errorLogRepository.save(createErrorLog(logDetails));
    }

    public ErrorLog createErrorLog(LogDetails logDetails) {
        return ErrorLog.builder()
                .errorDate(LocalDateTime.now())
                .errorMessage(logDetails.getLogMessage())
                .build();
    }
}
