package com.chriniko.example.notes.control;

import com.chriniko.example.logging.boundary.InfoLevel;

import javax.inject.Inject;
import java.util.function.Consumer;

public class SimpleNoteTracker implements NoteTracker {

    @Inject
    @InfoLevel
    Consumer<String> LOG;

    @Override
    public void track(String note) {
        LOG.accept(" >>> Note: " + note);
    }
}
