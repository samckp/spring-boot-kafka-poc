package com.kafka.poc.config;

import org.apache.kafka.clients.admin.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaAdminUtil {

    @Bean
    public AdminClient KafkaAdminBean(){
        Properties properties = new Properties();
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("security.protocol", "PLAINTEXT");
//        props.put("sasl.mechanism", "PLAINTEXT");
//        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='myuser' password='mypassword';");
//
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        AdminClient adminClient = AdminClient.create(props);
        return adminClient;
    }

}

