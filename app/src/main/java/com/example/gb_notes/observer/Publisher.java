package com.example.gb_notes.observer;

import com.example.gb_notes.data.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher(){
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifySingle(Note note){
        for (Observer observer: observers){
            observer.updateNoteData(note);
            unsubscribe(observer);
        }
    }
}
