package com.example.neo4j;

import com.example.neo4j.containers.Neo4j;
import com.example.neo4j.entities.ContextEntity;
import com.example.neo4j.entities.NodeEntity;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
class IssueTest {

    @Autowired
    private NodeService service;

    @Test
    void createAndUpdateTreeWithContext() {
        NodeEntity root = new NodeEntity();
        NodeEntity child = new NodeEntity();
        root.addChild(child);
        Tree tree = new Tree(root);

        // creation
        Tree created = service.saveTree(tree);
        assertThat(created.getRoot()).isNotNull();
        assertThat(created.getRoot().getUuid()).isNotNull();

        // update
        created.getRoot().setCode("ROOT");
        ContextEntity context = new ContextEntity();
        context.setPath(List.of(root, child));
        context.setValue("some value");
        created.setContexts(List.of(context));
        // created.getContexts().get(0).setValue("new value");
        // created.getContexts().get(0).setPath(List.of(root, child));
        Tree updated = service.saveTree(created); // -> clears relationshipEntityRegister

        assertThat(updated).isNotNull();
        assertThat(updated.getRoot().getUuid()).isEqualTo(created.getRoot().getUuid());
        assertThat(updated.getRoot().getChilds()).hasSize(1);
        assertThat(updated.getRoot().getCode()).isEqualTo("ROOT");
        assertThat(updated.getContexts().get(0).getValue()).isEqualTo("some value");

        // 3. find
        Optional<Tree> optFound = service.findTree(updated.getRoot().getUuid());

        assertThat(optFound).isNotEmpty();
        Tree found = optFound.orElseThrow();
        assertThat(found).isNotNull();
        assertThat(found.getRoot().getUuid()).isEqualTo(created.getRoot().getUuid());
        assertThat(found.getRoot().getChilds()).hasSize(1);
        assertThat(updated.getRoot().getCode()).isEqualTo("ROOT");
        assertThat(updated.getContexts().get(0).getValue()).isEqualTo("some value");
    }

    @TestConfiguration
    static class Config {
        @Bean
        public org.neo4j.ogm.config.Configuration configuration() {
            return new org.neo4j.ogm.config.Configuration.Builder().uri(Neo4j.database().getBoltUrl()).credentials("neo4j", Neo4j.database().getAdminPassword()).build();
        }
    }
}
