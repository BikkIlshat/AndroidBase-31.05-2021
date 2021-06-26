package ru.geekbrains.notesjournal.data;

public interface NoteSource {

    NoteData getCardData(int position);
    int getSize();
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);
    void addCardData(NoteData noteData);
    void clearNoteData();

}
