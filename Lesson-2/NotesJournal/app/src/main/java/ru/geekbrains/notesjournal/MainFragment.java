package ru.geekbrains.notesjournal;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.notesjournal.ui.NotesJournalFragment;


public class MainFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData noteData; // Текущая позиция (выбранная заметка)
    private boolean isLandscape;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        Configuration configuration = getResources().getConfiguration();
        isLandscape=configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_description_of_notes, container, false);
        TextInputEditText titleText = view.findViewById(R.id.note_title);
        TextInputEditText contentText = view.findViewById(R.id.note_content);
        TextView dateOfCreationText = view.findViewById(R.id.note_date_of_creation);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
        dateOfCreationText.setText(String.format("%s: %s", "Created", formatter.format(noteData.getCreationDate().getTime())));
        titleText.setText(noteData.getTitle());
        contentText.setText(noteData.getContent());
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




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, noteData);
        super.onSaveInstanceState(outState);
    }





}