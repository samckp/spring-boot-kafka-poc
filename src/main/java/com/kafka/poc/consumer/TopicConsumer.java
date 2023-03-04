package com.kafka.poc.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TopicConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicConsumer.class);

    private String payload;

    @KafkaListener(topics = "${topic_name}")
    public void consume(String msg){

        this.payload = msg;
        LOGGER.info(String.format("Message received --> %s", msg));
    }

    public String getPayload() {
        return payload;
    }
}
