package app.logs.generator.service.impl;

import app.logs.generator.model.LogData;
import app.logs.generator.model.LogEvent;
import app.logs.generator.model.random.*;
import app.logs.generator.service.LogEventGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LogEventGeneratorServiceImpl implements LogEventGeneratorService {

    private final Host host;

    private final App app;

    private final Path path;

    private final Severity severity;

    private final Message message;

    private final Random random;

    @Autowired
    public LogEventGeneratorServiceImpl(final Host host, final App app, final Path path, final Severity severity, final Message message) {
        this.host = host;
        this.app = app;
        this.path = path;
        this.severity = severity;
        this.message = message;
        random = new Random();
    }

    @Override
    public LogEvent generateLogEvent() {
        final LogEvent logEvent = new LogEvent();
        logEvent.setHost(getRandomValue(host));
        logEvent.setApp(getRandomValue(app));
        logEvent.setPath(getRandomValue(path));
        logEvent.setSeverity(getRandomValue(severity));
        logEvent.setMessage(getRandomValue(message));
        logEvent.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return logEvent;
    }

    private String getRandomValue(final LogData logData) {
        return logData.getValue(random.nextInt(logData.getSize()));
    }
}
