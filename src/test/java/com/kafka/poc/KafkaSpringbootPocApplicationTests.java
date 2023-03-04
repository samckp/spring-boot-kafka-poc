package com.kafka.poc;

import com.kafka.poc.consumer.TopicConsumer;
import com.kafka.poc.producer.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaSpringbootPocApplicationTests {

	@Autowired
	Producer producer;

	@Autowired
	TopicConsumer topicConsumer;

	@Value("${topic-name}")
	private String topic_name;

	@Test
	public void testProducerConsumer() throws Exception{

		String actual = "this is test msg";
		producer.sendMessage(topic_name, actual);

		Thread.sleep(20000);
		Assertions.assertEquals(actual, topicConsumer.getPayload());
	}

}
