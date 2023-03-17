package com.kafka.poc.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class ConsumerService {
    String bootstrapServers = "localhost:9092";

    public List<String> topicConsume(String topicName) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("enable.auto.commit", "false");
        props.put("group.id", "my-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        List<String> result =new ArrayList<>();
        consumer.subscribe(Arrays.asList(topicName));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n",
                    record.offset(), record.key(), record.value());
            result.add(record.value());
        }

        return result;
    }

}
