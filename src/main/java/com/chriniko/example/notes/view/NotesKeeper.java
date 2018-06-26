package com.chriniko.example.notes.view;

import com.chriniko.example.jsf.boundary.JsfEngine;
import com.chriniko.example.notes.control.StateKeeper;
import com.chriniko.example.notes.control.NoteTracker;
import com.chriniko.example.posts.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@StateKeeper
public class NotesKeeper implements Serializable {

    @Getter
    @Setter
    private List<String> notes;

    @Inject
    Instance<NotesKeeper> notesKeeperInstance;

    @Inject
    JsfEngine jsfEngine;

    @Getter
    @Setter
    private String newNote;

    @Inject
    NoteTracker noteTracker;

    // Note: comment/uncomment to see what happens and then see VetoEntity.java.
//    @Inject
//    Post post;

    @PostConstruct
    void init() {
        notes = new ArrayList<>();
    }

    public void destroy() {
        notesKeeperInstance.destroy(notesKeeperInstance.get());
        jsfEngine.displayMessage("Your notes have been destroyed successfully!");
    }

    public void save() {
        if (newNote == null || newNote.isEmpty()) {
            jsfEngine.displayWarnMessage("Please enter note first!");
            return;
        }

        notes.add(newNote);

        noteTracker.track(newNote);

        newNote = "";
        jsfEngine.displayMessage("Your note has been saved successfully!");
    }
}
