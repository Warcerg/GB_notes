package com.example.gb_notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gb_notes.MainActivity;
import com.example.gb_notes.Navigation;
import com.example.gb_notes.R;
import com.example.gb_notes.data.CardsSource;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.data.Notes;
import com.example.gb_notes.observer.Observer;
import com.example.gb_notes.observer.Publisher;


public class NotesListFragment extends Fragment {

    private static final int ANIMATION_DEFAULT_DURATION = 1000;
    private Note selectedNote;
    private Notes notes;
    private CardsSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNotes();
        data = notes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initRecyclerView(view, data);
        setHasOptionsMenu(true);
        return view;
    }

    private void initNotes() {
        notes = new Notes();
        notes.addNote(new Note("First entry", "Hello world!", "17.06.2021", 1));
        notes.addNote(new Note("To do list", "1) Do laundry\n2) Buy groceries\n3) Call parents", "18.06.2021", 2));
        notes.addNote(new Note("Movies to watch", "Jurassic Park\nAlien \nTerminator 2\n Robocop", "20.06.2021", 3));
        notes.addNote(new Note("Friends Birthdays dates", "Alex - 26.02\n Max - 04.07\n Nick - 01.09", "22.06.2021", 4));
        notes.addNote(new Note("Vacation", "buy tickets\nbook the hotel\n buy sunglasses", "17.06.2021", 5));
        notes.addNote(new Note("Homework", "1) Do homework #8\n2) Listen lesson 9\n3) Check the homework #9", "27.06.2021", 6));
        notes.addNote(new Note("Another entry", "just some text", "28.06.2021", 7));
        notes.addNote(new Note("Songs to learn to play", "1) Jamiroquai\n2) Anderson Paak\n3)Chic", "30.06.2021", 8));
        notes.addNote(new Note("Work related", "1) NTM inventory analysis\n 2)Kick-off project meeting\n 3) Winshuttle script for auto PO creation", "02.07.2021", 9));
        notes.addNote(new Note("Random note", "random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated. random text to be repeated", "01.07.2021", 10));
        notes.addNote(new Note("Last entry", "Hope this app works correctly", "29.06.2021", 11));
    }

    @Override
    public void onCreateContextMenu(@NonNull  ContextMenu menu, @NonNull  View v, @Nullable  ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()){
            case R.id.action_updateNote:
                navigation.addFragment(AddNoteFragment.newInstance(data.getNoteData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.updateNoteData(position, note);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_deleteNote:
                data.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView(View view, CardsSource data) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(data, this  );
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view1, position) -> {
            selectedNote = data.getNoteData(position);
            navigation.addFragment(NoteDetailsFragment.newInstance(selectedNote), true);
        });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(ANIMATION_DEFAULT_DURATION);
        animator.setChangeDuration(ANIMATION_DEFAULT_DURATION);
        animator.setRemoveDuration(ANIMATION_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();

    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                navigation.addFragment(new SettingsFragment(), true);
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_noteAdd:
                navigation.addFragment(AddNoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.addNoteData(note);
                        adapter.notifyItemInserted(data.getSize()-1);
                        recyclerView.scrollToPosition((data.getSize())-1);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}