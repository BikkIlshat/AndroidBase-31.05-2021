package ru.geekbrains.notesjournal.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import ru.geekbrains.notesjournal.MainActivity;
import ru.geekbrains.notesjournal.Navigation;
import ru.geekbrains.notesjournal.NoteFragment;
import ru.geekbrains.notesjournal.R;
import ru.geekbrains.notesjournal.data.NoteData;
import ru.geekbrains.notesjournal.data.NoteDataSourceImpl;
import ru.geekbrains.notesjournal.observer.Observer;
import ru.geekbrains.notesjournal.observer.Publisher;

public class NotesJournalFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;
    private NoteDataSourceImpl data;
    private NotesJournalAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;



    public static  NotesJournalFragment newInstance() {
        return new NotesJournalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (data == null) {
            data = new NoteDataSourceImpl(getResources()).init();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_journal_fragment, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        return view;
    }



    @Override
    public void onAttach(@NonNull @NotNull Context context) {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contex_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                navigation.addFragment(NoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NoteData noteData) {
                        data.addNoteData(noteData);
                        adapter.notifyItemInserted(data.getSize() - 1);
                        recyclerView.scrollToPosition(data.getSize() - 1);
                    }
                });
                return true;
            case R.id.action_clear:
                data.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.contnex_menu_2, menu);
    }



    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        final int position = adapter.getMenuPosition();
        switch(item.getItemId()){
            case R.id.action_update:
                navigation.addFragment(NoteFragment.newInstance(data.getNoteData(position)), true);
                publisher.subscribe(noteData -> {
                    data.updateNoteData(position, noteData);
                    adapter.notifyItemChanged(position);
                });
                return true;
            case R.id.action_delete:
                data.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
                return true;
            case R.id.action_clear:
                data.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;

        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.notes_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesJournalAdapter(data, this);
        adapter.setOnItemClickListener((view1, position) -> {
            navigation.addFragment(NoteFragment.newInstance(data.getNoteData(position)),
                    true);
            publisher.subscribe(note1 -> {
               data.clearNoteData();
                adapter.notifyItemChanged(position);
            });
        });

        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setChangeDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);




    }


}





