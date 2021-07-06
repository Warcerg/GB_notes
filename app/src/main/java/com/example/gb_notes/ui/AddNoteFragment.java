package com.example.gb_notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.gb_notes.MainActivity;
import com.example.gb_notes.R;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.observer.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;


public class AddNoteFragment extends Fragment {

    private static final String ARG_NOTE_DATA = "Param_NoteData";
    private static int noteIndex;

    private Note note;
    private Publisher publisher;

    private TextView index;
    private TextInputEditText heading;
    private TextInputEditText noteText;
    private DatePicker datePicker;

    public static AddNoteFragment newInstance(Note note) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_DATA, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddNoteFragment newInstance(int dataSize) {
        noteIndex = dataSize+1;
        AddNoteFragment fragment = new AddNoteFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        initView(view);

        if (note != null) {
            populateView();
        }
        return view;
    }


    private void initView(View view) {
        index = view.findViewById(R.id.textNoteIndex);
        heading = view.findViewById(R.id.textNoteHeading);
        noteText = view.findViewById(R.id.textNoteText);
        datePicker = view.findViewById(R.id.textNoteDate);
        Button saveButton = view.findViewById(R.id.buttonSaveNote);
        initButtonOnClickListener(saveButton);
    }

    private void initButtonOnClickListener(Button saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }


    private void populateView() {
        index.setText(Integer.toString(note.getNoteIndex()));
        heading.setText(note.getHeading());
        noteText.setText(note.getNoteText());
        initDatePicker((note.getDate()));

    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNoteData();
    }

    private Note collectNoteData() {
        Note answer;

        Editable headingRaw = this.heading.getText();
        String heading = headingRaw == null ? "" : headingRaw.toString();

        Editable noteTextRaw = this.noteText.getText();
        String noteText = noteTextRaw == null ? "" : noteTextRaw.toString();

        Date date = getDataFromDatePicker();

        String indexValue = this.index.getText().toString();
        int index;
        if (indexValue.equals("")) {
            index = noteIndex;
        } else {
            index = Integer.parseInt(indexValue);
        }

        if (note != null) {
            answer = new Note(heading, noteText, date, index);
            answer.setId(note.getId());
        } else {
            answer = new Note(heading, noteText, date, index);
        }
        return answer;
    }

    private Date getDataFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(Calendar.MONTH, this.datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }



}