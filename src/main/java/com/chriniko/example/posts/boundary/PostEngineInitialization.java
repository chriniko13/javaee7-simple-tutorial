package com.chriniko.example.posts.boundary;

import com.chriniko.example.posts.entity.Post;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Startup
@Singleton
public class PostEngineInitialization {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    void init() {

        List<Post> qaPosts = IntStream.rangeClosed(1, 100)
                .boxed()
                .map(idx -> new Post(UUID.randomUUID().toString(), "title_" + idx, "description_" + idx, Instant.now()))
                .collect(Collectors.toList());

        qaPosts.forEach(qaPost -> entityManager.persist(qaPost));
    }

}
