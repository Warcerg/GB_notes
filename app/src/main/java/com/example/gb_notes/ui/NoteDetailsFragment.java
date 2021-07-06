package com.example.gb_notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gb_notes.R;
import com.example.gb_notes.data.Note;

import java.text.SimpleDateFormat;


public class    NoteDetailsFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    private Note note;


    public static NoteDetailsFragment newInstance(Note note){
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX,note);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        TextView noteIndex = view.findViewById(R.id.textNoteIndex);
        TextView noteTheme = view.findViewById(R.id.textNoteHeading);
        TextView noteDate = view.findViewById(R.id.textNoteDate);
        TextView noteText = view.findViewById(R.id.textNoteText);


        noteIndex.setText(Integer.toString(note.getNoteIndex()));
        noteTheme.setText(note.getHeading());
        noteDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDate()));
        noteText.setText(note.getNoteText());

        return view;
    }



}