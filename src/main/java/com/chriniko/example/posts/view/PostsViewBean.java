package com.chriniko.example.posts.view;

import com.chriniko.example.jsf.boundary.JsfEngine;
import com.chriniko.example.posts.boundary.PostEngine;
import com.chriniko.example.posts.control.PostValidationException;
import com.chriniko.example.posts.entity.Post;
import com.chriniko.example.tracker.control.TriggeredMethod;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
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

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String text;

    @Inject
    Event<TriggeredMethod> triggeredMethodEvent;

    @Inject
    JsfEngine jsfEngine;

    @PostConstruct
    void init() {
        posts = fetchAllPosts();
    }

    public void refresh() {
        posts = fetchAllPosts();

        jsfEngine.displayMessage("Datatable refreshed!");
    }

    public void save() {

        try {

            postEngine.store(title, text);

            posts = fetchAllPosts();

            jsfEngine.displayMessage("Post saved & Datatable refreshed!");

        } catch (EJBException error) {

            if (error.getCause() instanceof PostValidationException) {

                PostValidationException validationError = (PostValidationException) error.getCause();
                jsfEngine.displayWarnMessage(validationError.getMessage());

            } else {
                jsfEngine.displayFatalMessage("There is a problem in our ends, please try again later!");
            }
        }

    }

    private List<Post> fetchAllPosts() {

        System.out.println("PostsViewBean#fetchAllPosts -- Thread = " + Thread.currentThread().getName());

        TriggeredMethod event = new TriggeredMethod(
                "PostsViewBean",
                "fetchAllPosts",
                Collections.emptyList());

        triggeredMethodEvent.fire(event);

        triggeredMethodEvent
                .fireAsync(event)
                .whenComplete((result, error) -> {
                    if (error == null) {
                        System.out.println("PostsViewBean#fetchAllPosts[COMPLETED-SUCCESS] -- Thread = " + Thread.currentThread().getName());
                    } else {
                        System.out.println("PostsViewBean#fetchAllPosts[COMPLETED-FAILURE] -- Thread = " + Thread.currentThread().getName());
                    }
                });

        return postEngine
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getTitle))
                .collect(Collectors.toList());
    }


}
