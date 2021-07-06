package com.example.gb_notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
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
import com.example.gb_notes.data.NoteSourceFirebaseImpl;
import com.example.gb_notes.data.CardSourceResponse;
import com.example.gb_notes.data.CardsSource;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.observer.ActionObserver;
import com.example.gb_notes.observer.ActionPublisher;
import com.example.gb_notes.observer.Observer;
import com.example.gb_notes.observer.Publisher;


public class NotesListFragment extends Fragment {

    private static final int ANIMATION_DEFAULT_DURATION = 1000;
    private Note selectedNote;
    private CardsSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private ActionPublisher actPublisher;


    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        data = new NoteSourceFirebaseImpl().init(new CardSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }


    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view1, position) -> {
            selectedNote = data.getNoteData(position);
            navigation.addFragment(NoteDetailsFragment.newInstance(selectedNote), true);
        });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
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
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
        actPublisher = activity.getActPublisher();

    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        actPublisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
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
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private boolean onItemSelected(int itemId) {
        int position;
        switch (itemId) {
            case R.id.action_settings:
                navigation.addFragment(new SettingsFragment(), true);
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_noteAdd:
                navigation.addFragment(AddNoteFragment.newInstance(data.getSize()), true);
                publisher.subscribe(note -> {
                    data.addNoteData(note);
                    adapter.notifyItemInserted(data.getSize() - 1);
                    recyclerView.scrollToPosition((data.getSize()) - 1);
                });
                return true;
            case R.id.action_updateNote:
                position = adapter.getMenuPosition();
                navigation.addFragment(AddNoteFragment.newInstance(data.getNoteData(position)), true);
                publisher.subscribe(note -> {
                    data.updateNoteData(position, note);
                    adapter.notifyItemChanged(position);
                });
                return true;
            case R.id.action_deleteNote:
                position = adapter.getMenuPosition();
                DialogFragment dialogFragment = new DialogNoteFragment();
                dialogFragment.show(getChildFragmentManager(), "Tag");
                actPublisher.subscribe(result -> {
                    if (result) {
                        data.deleteNoteData(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
                return true;
        }
        return false;
    }

}