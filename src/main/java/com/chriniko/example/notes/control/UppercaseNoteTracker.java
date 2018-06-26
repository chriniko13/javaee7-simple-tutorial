package com.chriniko.example.notes.control;

import com.chriniko.example.logging.boundary.InfoLevel;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.function.Consumer;

@Alternative
public class UppercaseNoteTracker implements NoteTracker {

    @Inject
    @InfoLevel
    Consumer<String> LOG;

    @Override
    public void track(String note) {
        LOG.accept(" >>> Note: " + note.toUpperCase());
    }
}
