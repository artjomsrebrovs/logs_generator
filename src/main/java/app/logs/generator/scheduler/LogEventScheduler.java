package app.logs.generator.scheduler;

import app.logs.generator.service.LogEventClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogEventScheduler {

    private final LogEventClientService logEventClientService;

    @Autowired
    public LogEventScheduler(final LogEventClientService logEventClientService) {
        this.logEventClientService = logEventClientService;
    }

    @Scheduled(fixedDelayString = "${message.sending.rate:1000}")
    public void run() {
        logEventClientService.emitLogEvent();
    }

}
