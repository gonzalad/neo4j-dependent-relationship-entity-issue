package com.example.neo4j;

import com.example.neo4j.containers.Neo4j;
import com.example.neo4j.entities.Actor;
import com.example.neo4j.entities.Movie;
import com.example.neo4j.entities.Role;
import com.example.neo4j.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
class IssueTest {

    @Autowired
    private ActorRepository repository;

    @Autowired
    private Session session;

    @Test
    void createAndUpdateTreeWithContext() {
        Movie movie = new Movie();
        movie.setTitle("Lord of the rings");
        Role role = new Role();
        role.setTitle("Saruman");
        role.setMovie(movie);
        Actor actor = new Actor();
        actor.setName("Christopher Lee");
        actor.setPlayedIn(role);

        Actor savedActor = repository.save(actor);
        assertThat(savedActor).isNotNull();
        assertThat(savedActor.getId()).isNotNull();

        Optional<Actor> loadedActor = repository.findActor(savedActor.getId());
        assertThat(loadedActor).isPresent();
        assertThat(loadedActor.get().getId()).isEqualTo(savedActor.getId());

        runReadQuery(savedActor);
    }

    private void runReadQuery(Actor entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        session.query("MATCH path = (a:Actor)-[:PLAYED_IN*0..]->()"
                + " WHERE id(a) = $id"
                + " RETURN nodes(path), relationships(path)", params);
    }

    @TestConfiguration
    static class Config {
        @Bean
        public org.neo4j.ogm.config.Configuration configuration() {
            return new org.neo4j.ogm.config.Configuration.Builder().uri(Neo4j.database().getBoltUrl()).credentials("neo4j", Neo4j.database().getAdminPassword()).build();
        }
    }
}
