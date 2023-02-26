package com.kafka.poc.controller;

import com.kafka.poc.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    Producer producer;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("msg") String msg ){

        producer.sendMessage(msg);
        return ResponseEntity.ok("Message sent to Topic");
    }
}
