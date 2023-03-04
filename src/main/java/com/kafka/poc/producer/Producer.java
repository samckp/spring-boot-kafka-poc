package com.kafka.poc.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    @Value("${topic-name}")
    private String topic_name;
    private KafkaTemplate<String, String> template;

    public Producer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendMessage(String topicName, String msg){
        LOGGER.info(String.format("Message sent %s", msg));
        template.send(topicName, msg);
    }
}
