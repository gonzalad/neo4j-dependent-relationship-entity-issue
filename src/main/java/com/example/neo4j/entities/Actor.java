package com.example.neo4j.entities;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample from https://docs.spring.io/spring-data/neo4j/docs/5.3.6.RELEASE/reference/html/#reference:annotating-entities:relationship-entity
 */
@NodeEntity
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type="PLAYED_IN")
    private List<Role> playedIn;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getPlayedIn() {
        return playedIn;
    }

    public void addPlayedIn(Role playedIn) {
        if (this.playedIn == null) {
            this.playedIn = new ArrayList<>();
        }
        playedIn.setActor(this);
        this.playedIn.add(playedIn);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
