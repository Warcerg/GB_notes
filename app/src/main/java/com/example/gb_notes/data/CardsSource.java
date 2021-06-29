package com.example.gb_notes.data;

public interface CardsSource {
    Note getNoteData(int position);
    int getSize();
    void deleteNoteData(int position);
    void updateNoteData(int position, Note note);
    void addNoteData(Note note);
}
