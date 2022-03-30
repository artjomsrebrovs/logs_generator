package app.logs.generator.service.impl;

import app.logs.generator.kafka.producer.KafkaProducerService;
import app.logs.generator.model.LogEvent;
import app.logs.generator.service.LogEventClientService;
import app.logs.generator.service.LogEventGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogEventClientServiceImpl implements LogEventClientService {

    private final Logger logger = LoggerFactory.getLogger(LogEventClientServiceImpl.class);

    @Value("${kafka.topic.name}")
    private String kafkaTopicName;

    private final LogEventGeneratorService logEventGeneratorService;

    private final KafkaProducerService kafkaProducerService;

    private final ObjectMapper objectMapper;

    @Autowired
    public LogEventClientServiceImpl(final LogEventGeneratorService logEventGeneratorService, final KafkaProducerService kafkaProducerService) {
        this.logEventGeneratorService = logEventGeneratorService;
        this.kafkaProducerService = kafkaProducerService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void emitLogEvent() {
        final String logEventJson = generateLogEventJson();
        kafkaProducerService.publishMessage(kafkaTopicName, logEventJson);
    }

    private String generateLogEventJson() {
        final LogEvent logEvent = logEventGeneratorService.generateLogEvent();

        try {
            return objectMapper.writeValueAsString(logEvent);

        } catch (JsonProcessingException e) {
            logger.error("Cant generate log event JSON", e);
            throw new IllegalArgumentException("Cant generate log event JSON", e);
        }
    }
}
