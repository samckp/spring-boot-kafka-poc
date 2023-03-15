package com.kafka.poc.controller;

import com.kafka.poc.producer.Producer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    Producer producer;

    @Autowired
    AdminClient adminClient;

    @Value("${topic-name}")
    private String topic_name;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("msg") String msg ){

        producer.sendMessage(topic_name, msg);
        return ResponseEntity.ok("Message sent to Topic");
    }
    
    @GetMapping("/create")
    public String createTopic(){

        System.out.println("Create topic ");
        NewTopic newTopic = new NewTopic("new-topic", 1, (short)1);
        adminClient.createTopics(Collections.singleton(newTopic));
        return "topic created !!";
    }

    @GetMapping("/topics")
    public ResponseEntity<Set<String>> getTopics(){

        Set<String> topicsList = null;
        try {

            topicsList = adminClient.listTopics().names().get();
            System.out.println(topicsList.stream().collect(Collectors.toList()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(topicsList);
    }

    @GetMapping("/acl")
    public void createAcl(){

        AccessControlEntry entry = new AccessControlEntry(
                "User:testuser",
                "IP:localhost",
                AclOperation.WRITE,
                AclPermissionType.ALLOW
        );
        ResourcePattern pattern = new ResourcePattern(
                ResourceType.TOPIC, "mytopic", PatternType.LITERAL);

        AclBinding aclBinding = new AclBinding(pattern, entry);

        try {
            adminClient.createAcls(Collections.singleton(aclBinding)).all().get(60, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }finally {
            adminClient.close();
        }
    }

}
