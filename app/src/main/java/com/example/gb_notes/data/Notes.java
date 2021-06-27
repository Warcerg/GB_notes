package com.example.gb_notes.data;


import java.util.ArrayList;

public class Notes implements CardsSource {

    private ArrayList<Note> notes;

    public Notes() {
        notes = new ArrayList<>();
    }

    public void addNote(Note note){
        notes.add(note);
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int getSize() {
        return notes.size();
    }
}
