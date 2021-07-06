package com.example.gb_notes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteSourceFirebaseImpl implements CardsSource {

    private static final String TAG = "[FirebaseImpl]";
    private static final String NOTES_COLLECTION = "notes";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);
    private CardSourceResponse cardSourceResponse;
    private List<Note> notesData = new ArrayList<>();

    @Override
    public CardsSource init(CardSourceResponse cardSourceResponse) {
        this.cardSourceResponse = cardSourceResponse;
        collection.orderBy(NoteDataMapping.Fields.NOTE_INDEX, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notesData = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteDataMapping.toNoteData(id, doc);
                                notesData.add(note);
                            }
                            Log.d(TAG, "success " + notesData.size() + " qnt");
                            cardSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });

        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notesData.get(position);
    }

    @Override
    public int getSize() {
        if (notesData == null) {
            return 0;
        } else {
            return notesData.size();
        }
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteDataMapping.toDocument(note)).addOnSuccessListener(d -> {
            cardSourceResponse.initialized(NoteSourceFirebaseImpl.this);
        });
    }

    @Override
    public void addNoteData(Note note) {
        collection.add(NoteDataMapping.toDocument(note))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        note.setId(documentReference.getId());
                        cardSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                    }
                });
    }
}
