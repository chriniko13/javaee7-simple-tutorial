package com.chriniko.example.posts.control;

import com.chriniko.example.posts.entity.Post;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Stream;

public class PostValidator {

    @PostConstruct
    void onCreation() {
        System.out.println("----Control, clazz: " + this.getClass().getSimpleName() + ", thread: " + Thread.currentThread().getName());
    }

    public void validate(Post post) {

        boolean missingRequiredField = Stream.of(post.getId(), post.getTitle(), post.getText())
                .anyMatch(Objects::isNull);

        if (missingRequiredField) {
            throw new PostValidationException("id, title and text are required fields for post!");
        }

    }

}
