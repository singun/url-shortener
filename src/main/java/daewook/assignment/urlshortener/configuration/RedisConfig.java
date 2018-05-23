package daewook.assignment.urlshortener.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    @Value("${redis.connection.host}")
    private String host;

    @Value("${redis.connection.port}")
    private Integer port;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }
}