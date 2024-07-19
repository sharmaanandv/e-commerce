package org.ecomm.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Slf4j
@Configuration
public class Test {

    //    @Value("${spring.cloud.gateway.routes}")
//    private List<Object> routes;
    @Autowired
    private Environment env;

    @Bean
    public String testbean() {
        log.info("routes {}", env.getProperty("spring.cloud.gateway.routes[0].uri"));
        return "";
    }

}
