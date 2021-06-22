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

import java.util.Date;


public class MainFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData currentNote; // Текущая позиция (выбранная заметка)
    private boolean isLandscape;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        Configuration configuration = getResources().getConfiguration();
        isLandscape=configuration.orientation==Configuration.ORIENTATION_LANDSCAPE;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_main, container, false);
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
       // initList(view);
    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new NoteData("No name", getResources().getStringArray(R.array.notes)[0], new Date());
        }

        if (isLandscape) {
            showDescriptionOfNotes(currentNote);
        }

    }

    private void showDescriptionOfNotes(NoteData currentNotes) {
        if (isLandscape) {
            showLandDescriptionOfNotes(currentNotes);
        } else {
            showPortDescriptionOfNotes(currentNotes);
        }
    }


    private void showLandDescriptionOfNotes(NoteData currentNotes) {
        DescriptionOfNotesFragment detail = DescriptionOfNotesFragment.newInstance(currentNotes);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();


    }


    // как нам этот фрагмент вызывать ? перейдем в -> activity_main
    // Делаем обработку нажатия на наши заметки
    // Показываем в портретной  ориентации

    private void showPortDescriptionOfNotes(NoteData noteData) {
        DescriptionOfNotesFragment detail = DescriptionOfNotesFragment.newInstance(noteData);

        String backStateName = this.getClass().getName();
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fl_notes_container, detail);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

}