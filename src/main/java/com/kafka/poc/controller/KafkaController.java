package com.kafka.poc.controller;

import com.kafka.poc.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    Producer producer;
    @Value("${topic-name}")
    private String topic_name;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("msg") String msg ){

        producer.sendMessage(topic_name, msg);
        return ResponseEntity.ok("Message sent to Topic");
    }
}
