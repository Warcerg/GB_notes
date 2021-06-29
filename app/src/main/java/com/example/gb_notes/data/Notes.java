package com.example.gb_notes.data;


import java.util.ArrayList;

public class Notes implements CardsSource {

    private ArrayList<Note> notes;
    private static int noteIndex = 0;

    public Notes() {
        notes = new ArrayList<>();
    }

    public void addNote(Note note){
        notes.add(note);
        noteIndex++;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int getSize() {
        return notes.size();
    }


    @Override
    public void deleteNoteData(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        notes.set(position, note);
    }

    @Override
    public void addNoteData(Note note) {
        addNote(note);
    }

    public void clear(){
        notes.clear();
    }

    public static int getNoteIndex() {
        return noteIndex;
    }

}
