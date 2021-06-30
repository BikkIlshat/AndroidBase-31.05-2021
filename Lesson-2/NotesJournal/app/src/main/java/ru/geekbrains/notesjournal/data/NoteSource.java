package ru.geekbrains.notesjournal.data;

public interface NoteSource {

    NoteSource init(NoteSourceResponse noteSourceResponse);
    NoteData getNoteData(int position);
    int getSize();
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);
    void addNoteData(NoteData noteData);
    void clearNoteData();

}
