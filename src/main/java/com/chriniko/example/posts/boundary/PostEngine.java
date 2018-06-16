package com.chriniko.example.posts.boundary;


import com.chriniko.example.configurator.boundary.Threshold;
import com.chriniko.example.logging.boundary.InfoLevel;
import com.chriniko.example.posts.control.PostValidator;
import com.chriniko.example.posts.crosscut.Logged;
import com.chriniko.example.posts.entity.Post;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


@Stateless
@Logged
public class PostEngine {

    @Inject
    PostValidator postValidator;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @InfoLevel
    Consumer<String> LOG;

    @Inject
    @Threshold
    String threshold;

    @PostConstruct
    void onCreation() {
        LOG.accept("----Boundary, clazz: " + this.getClass().getSimpleName() + ", thread: " + Thread.currentThread().getName());
    }

    public void store(String title, String text) {
        Post newPost = new Post(UUID.randomUUID().toString(), title, text, Instant.now());
        postValidator.validate(newPost);
        entityManager.persist(newPost);
    }

    public List<Post> findAll() {

        LOG.accept("threshold is: " + threshold);
        LOG.accept("findAll called!");

        TypedQuery<Post> q = entityManager.createQuery("select p from Post p", Post.class);
        return q.getResultList();
    }

}
