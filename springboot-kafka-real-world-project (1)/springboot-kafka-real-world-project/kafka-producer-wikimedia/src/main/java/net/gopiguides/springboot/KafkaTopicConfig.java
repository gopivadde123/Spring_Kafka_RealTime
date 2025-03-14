package net.gopiguides.springboot;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration // spring make this class as configuration class
public class KafkaTopicConfig {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("wikimedia_recentchange")
                .build();
    }
}
