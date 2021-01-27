package com.example.neo4j.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

/**
 * See:
 * <p>
 * https://medium.com/neo4j/testing-your-neo4j-based-java-application-34bef487cc3c
 * <p>
 * https://www.testcontainers.org/quickstart/junit_4_quickstart/
 * <p>
 * https://www.testcontainers.org/modules/databases/neo4j/
 */
public class Neo4j {
    private static final Logger log = LoggerFactory.getLogger(Neo4j.class);
    private static final String NEO4J_VERSION = "4.0.6";
    private static final String NEO4J_DOCKER_IMAGE = "neo4j:" + NEO4J_VERSION;

    private static Neo4jContainer neo4jContainer;

    public static Neo4jContainer database() {
        if (neo4jContainer == null) {
            neo4jContainer = new Neo4jContainer(NEO4J_DOCKER_IMAGE).withAdminPassword(null);
            neo4jContainer.withLogConsumer(new Slf4jLogConsumer(log));
            neo4jContainer.start();
        }
        return neo4jContainer;
    }

    /*
     * @TestConfiguration static class Config { }
     */
}
