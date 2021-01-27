package com.example.neo4j;

import com.example.neo4j.containers.Neo4j;
import com.example.neo4j.entities.ContextEntity;
import com.example.neo4j.entities.NodeEntity;
import com.example.neo4j.entities.RelationEntity;
import com.example.neo4j.service.NodeService;
import com.example.neo4j.service.Tree;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
class IssueTest {

    @Autowired
    private NodeService service;

    @Test
    void createSimpleTree() {

        Tree tree = new Tree(new NodeEntity());
        Tree saved = service.saveTree(tree);

        assertThat(saved).isNotNull();
        assertThat(saved.getRoot().getUuid()).isNotNull();

        Optional<Tree> found = service.findTree(saved.getRoot().getUuid());

        assertThat(found).isPresent();
        assertThat(found.orElseThrow().getRoot().getUuid()).isEqualTo(saved.getRoot().getUuid());
    }

    @Test
    void createTreeWithContext() {

        NodeEntity root = new NodeEntity();
        NodeEntity child = new NodeEntity();
        root.addChild(child);
        ContextEntity context = new ContextEntity();
        context.setPath(List.of(root, child));
        context.setValue("some value");
        Tree tree = new Tree(root, List.of(context));
        Tree saved = service.saveTree(tree);

        assertThat(saved).isNotNull();
        assertThat(saved.getRoot().getUuid()).isNotNull();
        assertThat(saved.getRoot().getChilds()).hasSize(1);
        assertThat(saved.getContexts()).hasSize(1);
        assertThat(saved.getContexts().get(0).getValue()).isEqualTo(context.getValue());
    }

    @Test
    void givenUUidNotExistsWhenFindThenVerifyEmpty() {

        Optional<Tree> found = service.findTree(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @TestConfiguration
    static class Config {
        @Bean
        public org.neo4j.ogm.config.Configuration configuration() {
            return new org.neo4j.ogm.config.Configuration.Builder().uri(Neo4j.database().getBoltUrl()).credentials("neo4j", Neo4j.database().getAdminPassword()).build();
        }
    }
}
