package com.example.neo4j.entities;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@org.neo4j.ogm.annotation.NodeEntity
public class ContextEntity {
    @GeneratedValue
    @Id
    private Long id;

    private String value;

    @Relationship(type = "PATH")
    private List<NodeEntity> path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<NodeEntity> getPath() {
        return path;
    }

    public void setPath(List<NodeEntity> path) {
        this.path = path;
    }
}
