package app.logs.generator.kafka.producer;

public interface KafkaProducerService {

    void publishMessage(String topic, String message);
}
