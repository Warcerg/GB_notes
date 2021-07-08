package com.example.gb_notes.observer;

import com.example.gb_notes.data.Note;

import java.util.ArrayList;
import java.util.List;

public class ActionPublisher {
    private List<ActionObserver> actObservers;

    public ActionPublisher(){
        actObservers = new ArrayList<>();
    }

    public void subscribe(ActionObserver actObserver){
        actObservers.add(actObserver);
    }

    public void unsubscribe(ActionObserver actObserver){
        actObservers.remove(actObserver);
    }

    public void notifySingle(Boolean result){
        for (ActionObserver actionObserver: actObservers){
            actionObserver.updateActionDecision(result);
            unsubscribe(actionObserver);
        }
    }
}
