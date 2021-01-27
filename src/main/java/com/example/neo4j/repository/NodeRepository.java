package com.example.neo4j.repository;

import com.example.neo4j.entities.NodeEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;
import java.util.UUID;

public interface NodeRepository extends Neo4jRepository<NodeEntity, Long> {
    //@formatter:off
    @Query("MATCH tree = (root:NodeEntity)-[:HAS_CHILD*0..]->(:NodeEntity)\n"
            + "WHERE root.uuid = $id\n"
            + "RETURN root, collect(nodes(tree)), collect(relationships(tree))"
//            + "RETURN DISTINCT child, nodes(tree), relationships(tree)"
    )
    //@formatter:on
    Optional<NodeEntity> findTree(UUID id);
}
