package com.kafka.poc.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TopicConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicConsumer.class);

    @KafkaListener(topics = "${topic_name}")
    public void consume(String msg){

        LOGGER.info(String.format("Message received --> %s", msg));
    }

}
