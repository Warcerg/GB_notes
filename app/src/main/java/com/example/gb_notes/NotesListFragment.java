package com.example.gb_notes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Notes selectedNote;
    private List<Notes> notesArray = new ArrayList<>();
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Notes note1 = new Notes("First entry", "Hello world!", "17.06.2021", 1);
        Notes note2 = new Notes("To do list", "1) Do laundry\n2) Buy groceries\n3) Call parents", "18.06.2021", 2);
        Notes note3 = new Notes("Movies to watch", "Jurassic Park\nAlien \nTerminator 2\n Robocop", "20.06.2021", 3);
        Notes note4 = new Notes("Friend's Birthdays dates", "Alex - 26.02\n Max - 04.07\n Nick - 01.09", "22.06.2021", 4);
        Notes note5 = new Notes("Vacation", "buy tickets\nbook the hotel\n buy sunglasses", "17.06.2021", 5);
        notesArray.add(note1);
        notesArray.add(note2);
        notesArray.add(note3);
        notesArray.add(note4);
        notesArray.add(note5);

        initList(view);

    }
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        for(Notes note : notesArray ){
            TextView tv = new TextView(getContext());
            String tvString = note.getNoteIndex() + ": " + note.getHeading();
            tv.setText(tvString);
            tv.setTextSize(30);
            layoutView.addView(tv);
            tv.setOnClickListener(v -> {
                selectedNote = note;
                showNoteDetails(selectedNote);
            });

        }
    }

    private void showNoteDetails(Notes selectedNote) {
        if (isLandscape){
            showLandNoteDetails(selectedNote);
        } else {
            showPortNoteDetails(selectedNote);
        }
    }

    private void showPortNoteDetails(Notes selectedNote) {
        NoteDetailsFragment details = NoteDetailsFragment.newInstance(selectedNote);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.mainFragment, details);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void showLandNoteDetails(Notes selectedNote) {
        NoteDetailsFragment details = NoteDetailsFragment.newInstance(selectedNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameNoteDetails, details);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, selectedNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            selectedNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            selectedNote = notesArray.get(0);
        }

        if (isLandscape) {
            showLandNoteDetails(selectedNote);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        notesArray.clear();
    }
}