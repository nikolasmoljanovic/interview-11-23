package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
