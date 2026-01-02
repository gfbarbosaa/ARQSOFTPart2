package pt.psoft.g1.psoftg1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public @Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange authorExchange() {
        return new TopicExchange("author.exchange");
    }
}
