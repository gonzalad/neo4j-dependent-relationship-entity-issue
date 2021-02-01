package com.example.neo4j.repository;

import com.example.neo4j.entities.Actor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ActorRepository extends Neo4jRepository<Actor, Long> {
    //@formatter:off
    @Query("MATCH path = (a:Actor)-[:PLAYED_IN*0..]->(:Movie)\n"
            + "WHERE id(a) = $id\n"
            + "RETURN a, collect(nodes(path)), collect(relationships(path))"
//            + "RETURN DISTINCT child, nodes(tree), relationships(tree)"
    )
    //@formatter:on
    Optional<Actor> findActor(Long id);
}
