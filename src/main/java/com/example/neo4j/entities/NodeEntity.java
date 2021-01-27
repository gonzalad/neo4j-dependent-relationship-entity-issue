package com.example.neo4j.entities;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@org.neo4j.ogm.annotation.NodeEntity
public class NodeEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Convert(UuidStringConverter.class)
    private UUID uuid;

    private String code;

    private List<RelationEntity> childs;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RelationEntity> getChilds() {
        return childs;
    }

    public void setChilds(List<RelationEntity> childs) {
        this.childs = childs;
    }

    public Stream<NodeEntity> stream() {
        return getChilds() != null ? getChilds().stream()
                .map(RelationEntity::getNode)
                .flatMap(NodeEntity::selfAndDescendants) : Stream.empty();
    }

    public Stream<NodeEntity> selfAndDescendants() {
        return Stream.concat(Stream.of(this), stream()).distinct();
    }

    public void addChild(NodeEntity child) {
        RelationEntity relation = new RelationEntity();
        relation.setParent(this);
        relation.setNode(child);
        if (this.childs == null) {
            this.childs = new ArrayList<>();
        }
        this.childs.add(relation);
    }
}
