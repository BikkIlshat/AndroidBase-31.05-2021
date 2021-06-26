package ru.geekbrains.notesjournal.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.geekbrains.notesjournal.R;

public class NoteDataSourceImpl implements  NoteSource {
    private List<NoteData> noteDataSource;
    private Resources resources;


    public NoteDataSourceImpl(Resources resources) {
        this.resources = resources;
        noteDataSource = new ArrayList<>(7);
    }


    public NoteDataSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.descriptions);


        for(int i = 0; i < descriptions.length; i++){
            noteDataSource.add(new NoteData(titles[i], descriptions[i], Calendar.getInstance().getTime()));
        }

        return this;
    }

    @Override
    public NoteData getCardData(int position) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void deleteNoteData(int position) {

    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {

    }

    @Override
    public void addCardData(NoteData noteData) {

    }

    @Override
    public void clearNoteData() {

    }
}
