package com.chriniko.example.posts.control;

import com.chriniko.example.posts.entity.Post;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

public class PostValidator {

    @PostConstruct
    void onCreation() {
        System.out.println("----Control, clazz: " + this.getClass().getSimpleName() + ", thread: " + Thread.currentThread().getName());
    }

    public void validate(Post post) {

        boolean missingRequiredField = Stream.of(post.getTitle(), post.getText())
                .anyMatch(info -> info == null || info.isEmpty());

        if (missingRequiredField) {
            throw new PostValidationException("title and text are required fields for the post!");
        }

    }

}
