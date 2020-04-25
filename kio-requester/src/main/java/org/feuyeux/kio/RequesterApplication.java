package org.feuyeux.kio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.io.IOException;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
@SpringBootApplication
public class RequesterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequesterApplication.class);
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder) throws IOException {
        return builder.connectTcp("localhost", 7878).block();
    }
}

