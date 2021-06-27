package ru.geekbrains.notesjournal.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.geekbrains.notesjournal.R;

public class NotesJournalFragment extends Fragment {

    public static  NotesJournalFragment newInstance() {
        return new NotesJournalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notes_journal_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notes_recycler_view);
        String[] data = getResources().getStringArray(R.array.notes);
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data) {

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NotesJournalAdapter adapter = new NotesJournalAdapter(data);
        recyclerView.setAdapter(adapter);
        // Установим слушателя


        adapter.SetOnItemClickListener((view, position) -> {


        });


        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.separator)));
        recyclerView.addItemDecoration(itemDecoration);
    }










}
