package com.example.neo4j;

import com.example.neo4j.containers.Neo4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class Config {
    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder().uri(Neo4j.database().getBoltUrl()).credentials("neo4j", Neo4j.database().getAdminPassword()).build();
    }
}
