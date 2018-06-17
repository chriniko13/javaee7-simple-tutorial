package com.chriniko.example.posts.control;

import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.transaction.TransactionScoped;
import java.io.Serializable;
import java.time.Instant;

@TransactionScoped
public class PostCreationTime implements Serializable {

    @Getter
    private Instant created;

    @PostConstruct
    void init() {
        created = Instant.now();
    }
}
