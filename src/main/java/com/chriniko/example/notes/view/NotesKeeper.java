package com.chriniko.example.notes.view;

import com.chriniko.example.jsf.boundary.JsfEngine;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
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
        newNote = "";
        jsfEngine.displayMessage("Your note has been saved successfully!");
    }
}
