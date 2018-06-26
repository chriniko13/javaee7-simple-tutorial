package com.chriniko.example.notes.control;

import java.io.Serializable;

public interface NoteTracker extends Serializable {

    void track(String note);
}
