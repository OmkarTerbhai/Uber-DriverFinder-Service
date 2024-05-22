package com.uber.driverfinder.services;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        this.kafkaTemplate.send(topic, message);
    }
}
