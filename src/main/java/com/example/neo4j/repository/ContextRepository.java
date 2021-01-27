package com.example.neo4j.repository;

import com.example.neo4j.entities.ContextEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.UUID;

public interface ContextRepository extends Neo4jRepository<ContextEntity, Long> {

    //@formatter:off
    @Query("MATCH path = (root:NodeEntity)-[:HAS_CHILD*1..]->(child:NodeEntity)\n"
            + "WHERE \n"
            + "(root.idDechildition = $id)\n"
            + "AND EXISTS((root)<-[:PATH]->(:ContextEntity))\n"
            + "AND EXISTS((child)<-[:PATH]->(:ContextEntity))\n"
            + "WITH path\n"
            + "MATCH (ctx:ContextEntity)\n"
            + "WHERE\n"
            + "  ALL (n IN nodes(path) WHERE (ctx)-[:PATH]->(n))\n"
            + "  AND size((ctx)-[:PATH]->(:NodeEntity)) = size(nodes(path))\n"
            + "RETURN ctx, path, nodes(path) AS chemin, relationships(path)"
    )
    //@formatter:on
    List<ContextEntity> findAllContextByRoot(UUID id);
}
