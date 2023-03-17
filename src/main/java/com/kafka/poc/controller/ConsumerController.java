package com.kafka.poc.controller;

import com.kafka.poc.consumer.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consume")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/{topicname}")
    public ResponseEntity<List<String>> consume(@PathVariable("topicname") String topicName){

        List<String> result = consumerService.topicConsume(topicName);

        return ResponseEntity.ok(result);
    }
}
