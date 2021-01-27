package com.example.neo4j.entities;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity("HAS_CHILD")
public class RelationEntity {

    @GeneratedValue
    @Id
    private Long id;

    private boolean required;

    @StartNode
    private NodeEntity parent;

    @EndNode
    private NodeEntity node;

    public RelationEntity() {
    }

    public void setNode(NodeEntity node) {
        this.node = node;
    }

    public NodeEntity getNode() {
        return node;
    }

    public NodeEntity getParent() {
        return parent;
    }

    public void setParent(NodeEntity parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
