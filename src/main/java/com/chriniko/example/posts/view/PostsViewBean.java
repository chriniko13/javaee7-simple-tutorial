package com.chriniko.example.posts.view;

import com.chriniko.example.jsf.boundary.JsfEngine;
import com.chriniko.example.language.boundary.LanguageEngine;
import com.chriniko.example.language.control.Language;
import com.chriniko.example.posts.boundary.PostEngine;
import com.chriniko.example.posts.control.PostValidationException;
import com.chriniko.example.posts.entity.Post;
import com.chriniko.example.tracker.control.TriggeredMethod;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
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

    @Getter
    @Setter
    private String welcomeMessage;

    @Inject
    Event<TriggeredMethod> triggeredMethodEvent;

    @Inject
    JsfEngine jsfEngine;

    // Note: Option 1
    //@Inject
    //@Language(Language.LangChoice.GREEK)
    //LanguageEngine languageEngine;

    // Note: Option 2
    @Inject
    @Any
    Instance<LanguageEngine> languageEngines;

    @PostConstruct
    void init() {
        posts = fetchAllPosts();

        // Note: Option 1
        //welcomeMessage = languageEngine.welcomeMessage();

        // Note: Option 2
        welcomeMessage = languageEngines
                .stream()
                .map(LanguageEngine::welcomeMessage)
                .collect(Collectors.joining(" / "));

    }

    public void refresh() {
        posts = fetchAllPosts();
        jsfEngine.displayMessage("Datatable refreshed!");
    }

    public void delete() {
        if (selectedPost == null) {
            jsfEngine.displayWarnMessage("Please select a post first.");
            return;
        }

        try {
            postEngine.delete(selectedPost.getId());
            posts = fetchAllPosts();
            jsfEngine.displayMessage("Post deleted & Datatable refreshed!");
        } catch (EJBException erro) {
            jsfEngine.displayFatalMessage("There is a problem in our ends, please try again later!");
        }
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
