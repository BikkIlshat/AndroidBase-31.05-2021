package ru.geekbrains.notesjournal.observer;


import ru.geekbrains.notesjournal.data.NoteData;

public interface Observer {

    void updateNoteData(NoteData noteData);
}
