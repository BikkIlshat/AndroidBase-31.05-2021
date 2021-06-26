package ru.geekbrains.notesjournal;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.geekbrains.notesjournal.observer.Publisher;
import ru.geekbrains.notesjournal.data.NoteData;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData noteData;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    private boolean isLandscape;




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


        Configuration configuration = getResources().getConfiguration();
        isLandscape=configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_description_of_notes, container, false);
        TextInputEditText titleText = view.findViewById(R.id.note_title);
        TextInputEditText contentText = view.findViewById(R.id.note_content);
        TextView dateOfCreationText = view.findViewById(R.id.title);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
//        dateOfCreationText.setText(String.format("%s: %s", "Created", formatter.format(noteData.getCreationDate().getTime())));
//        titleText.setText(noteData.getTitle());
//        contentText.setText(noteData.getContent());
        setHasOptionsMenu(true);
        return view;
    }

    // Обратите внимание, что в ниже приведенном  методе onViewCreated() вызывается метод initList(), в котором
    //создаётся список динамических элементов из массива строк, описанного в ресурсах.
    // вызывается после создания макета фрагмента, здесь мы проинициализируем список



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            Toast.makeText(getContext(), "Chosen add",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    // Ниже инициилизируем список:
    @Override // когда создали  метод  public View onCreateView - этот  же самый View прилетает в параметы нижеприведенного метода  (@NonNull View view, @Nullable Bundle savedInstanceState)
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // Обязательно вызываем какую-нибудь передыдущую функцию. (onViewCreated - это базовая функция)
    // что бы проинициилизировать список создаём последовательность:

    }




//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putParcelable(CURRENT_NOTE, noteData);
//        super.onSaveInstanceState(outState);
//    }





}