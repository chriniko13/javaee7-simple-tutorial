package com.chriniko.example.posts.view;

import com.chriniko.example.posts.boundary.PostEngine;
import com.chriniko.example.posts.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class PostsViewBean implements Serializable {

    @Inject
    PostEngine postEngine;

    @Setter
    @Getter
    private List<Post> posts;

    @Setter
    @Getter
    private Post selectedPost;

    @PostConstruct
    void init() {
        posts = postEngine
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getTitle))
                .collect(Collectors.toList());
    }


}
