package com.example.neo4j.service;

import com.example.neo4j.entities.ContextEntity;
import com.example.neo4j.entities.NodeEntity;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private NodeEntity root;

    private List<ContextEntity> contexts;

    public Tree(NodeEntity root) {
        this(root, new ArrayList<>());
    }

    public Tree(NodeEntity root, List<ContextEntity> contexts) {
        this.root = root;
        this.contexts = contexts;
    }

    public NodeEntity getRoot() {
        return root;
    }

    public void setRoot(NodeEntity root) {
        this.root = root;
    }

    public List<ContextEntity> getContexts() {
        return contexts;
    }

    public void setContexts(List<ContextEntity> contexts) {
        this.contexts = contexts;
    }
}
