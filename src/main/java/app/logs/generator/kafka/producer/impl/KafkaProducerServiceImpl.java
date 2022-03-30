package app.logs.generator.kafka.producer.impl;

import app.logs.generator.kafka.producer.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerServiceImpl(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishMessage(final String topic, final String message) {
        final ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, message);

        result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(final Throwable throwable) {
                logger.error("Error sending message to kafka", throwable);
            }

            @Override
            public void onSuccess(final SendResult<String, String> stringStringSendResult) {
                logger.info("Message sent to kafka: {}", stringStringSendResult);
            }
        });
    }
}
