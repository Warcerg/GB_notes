package com.example.gb_notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gb_notes.NoteDetailsFragment;
import com.example.gb_notes.R;
import com.example.gb_notes.data.CardsSource;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.data.Notes;
import com.example.gb_notes.ui.NotesAdapter;


public class NotesListFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note selectedNote;
    private Notes notes = new Notes();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initNotes();
        CardsSource data = notes;
        initRecyclerView(view, data);
        return view;
    }

    private void initNotes() {
        notes.addNote(new Note("First entry", "Hello world!", "17.06.2021", 1));
        notes.addNote(new Note("To do list", "1) Do laundry\n2) Buy groceries\n3) Call parents", "18.06.2021", 2));
        notes.addNote(new Note("Movies to watch", "Jurassic Park\nAlien \nTerminator 2\n Robocop", "20.06.2021", 3));
        notes.addNote(new Note("Friends Birthdays dates", "Alex - 26.02\n Max - 04.07\n Nick - 01.09", "22.06.2021", 4));
        notes.addNote(new Note("Vacation", "buy tickets\nbook the hotel\n buy sunglasses", "17.06.2021", 5));
        notes.addNote(new Note("Homework", "1) Do homework #8\n2) Listen lesson 9\n3) Check the homework #9", "27.06.2021", 6));
        notes.addNote(new Note("Another entry", "just some text", "28.06.2021", 7));
        notes.addNote(new Note("Music to learn to play", "1) Jamiroquai\n2) Anderson Paak\n3)Chic", "30.06.2021", 8));
        notes.addNote(new Note("Work related", "1) NTM inventory analysis\n 2)Kick-off project meeting\n 3) Winshuttle script for auto PO creation", "02.07.2021", 9));
        notes.addNote(new Note("Random note", "random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated", "01.07.2021", 10));
        notes.addNote(new Note("Last entry", "Hope this app works correctly", "29.06.2021", 11));
    }

    private void initRecyclerView(View view, CardsSource data) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NotesAdapter adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view1, position) -> {
            selectedNote = data.getNoteData(position);
            showPortNoteDetails(selectedNote);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showPortNoteDetails(Note selectedNote) {
        NoteDetailsFragment details = NoteDetailsFragment.newInstance(selectedNote);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.mainFragment, details);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, selectedNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            selectedNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            selectedNote = notes.getNoteData(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}