package ru.geekbrains.notesjournal;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

import ru.geekbrains.notesjournal.data.NoteData;
import ru.geekbrains.notesjournal.observer.Publisher;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";

    private NoteData noteData;
    private Publisher publisher;
    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;


    public static NoteFragment newInstance(NoteData noteData) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description_of_notes, container, false);
        initView(view);

        if (noteData != null) {
            populateView();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        noteData = collectNoteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteData);
    }


    private NoteData collectNoteData(){
        Editable titleRaw = this.title.getText();
        String title = titleRaw == null ? "" : titleRaw.toString();

        Editable descriptionRaw = this.description.getText();
        String description = descriptionRaw == null ? "" : descriptionRaw.toString();

        Date date = getDateFromDatePicker();

        NoteData answer;
        if (noteData != null) {
            answer = new NoteData(title, description, date);
            answer.setId(noteData.getId());

        } else {

            answer = new NoteData(title,description,date);
        }
        return answer;
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void populateView() {
        title.setText(noteData.getTitle());
        description.setText(noteData.getDescription());
        initDatePicker(noteData.getDate());
    }

    private void initView(View view) {
        title = view.findViewById(R.id.note_title);
        description = view.findViewById(R.id.note_content);
        datePicker = view.findViewById(R.id.inputDate);
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