package com.example.gb_notes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoteDetailsFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    private Notes note;
    private boolean isLandscape;

    public static NoteDetailsFragment newInstance(Notes note){
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX,note);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if( getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        }
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
        noteDate.setText(note.getDate());
        noteText.setText(note.getNoteText());
        return view;
    }


}