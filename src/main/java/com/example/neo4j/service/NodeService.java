package com.example.neo4j.service;

import com.example.neo4j.entities.ContextEntity;
import com.example.neo4j.entities.NodeEntity;
import com.example.neo4j.repository.ContextRepository;
import com.example.neo4j.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class NodeService {

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private NodeRepository nodeRepository;

    public Tree saveTree(Tree tree) {
        initializeUuidIfNeeded(tree);
        NodeEntity savedNode = nodeRepository.save(tree.getRoot());
        //
        // nodeRepository.findTree(tree.getRoot().getUuid());
        // contextRepository.findAllContextByRoot(tree.getRoot().getUuid());
        // tree.getContexts().get(0).setPath(tree.getRoot().selfAndDescendants().collect(Collectors.toList()));
        //
        Iterable<ContextEntity> savedContextIterable = contextRepository.saveAll(tree.getContexts());
        return new Tree(savedNode, toList(savedContextIterable));
    }

    private void initializeUuidIfNeeded(Tree tree) {
        tree.getRoot().selfAndDescendants()
                .filter(it -> it.getUuid() == null)
                .distinct()
                .forEach(it -> it.setUuid(UUID.randomUUID()));
    }

    public Optional<Tree> findTree(UUID id) {
        Optional<NodeEntity> root = nodeRepository.findTree(id);
        List<ContextEntity> contexts = root.map(r -> contextRepository.findAllContextByRoot(r.getUuid())).orElse(List.of());
        return root.map(r -> new Tree(r, contexts));
    }

    private <E> List<E> toList(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
