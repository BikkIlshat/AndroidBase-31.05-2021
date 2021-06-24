package ru.geekbrains.notesjournal;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;


public class DescriptionOfNotesFragment extends Fragment {

    public static final String KEY_ARG = "key_arg";


    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    // newInstance - метод который умеет создавать объект. Этот метод на на входе получает параметр  индекс элемента массива



    public static DescriptionOfNotesFragment newInstance(NoteData noteData) {
        DescriptionOfNotesFragment fragment = new DescriptionOfNotesFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*
    inflater.inflate -  работая с с xml можем указать его в методе что бы он был в виде объекта.
    Превращает наш  fragment_description_of_notes в объект, в данном случае будет объектом  Java-вского элемнта View
    и который обратно передадим системе  - и вот этот View будет являться фрагментом.
    Метод onCreateView во фрагменте — аналог метода setContentView в активити.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // подправляем код  return inflater.inflate(R.layout.fragment_description_of_notes, container, false); на ниже приведенный:
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_description_of_notes, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        TextView Name = view.findViewById(R.id.textView_name);
        TextView describe = view.findViewById(R.id.textView_describe);
        TextView date = view.findViewById(R.id.textView_date);

        if (getArguments() != null) {
            NoteData noteData = getArguments().getParcelable(KEY_ARG);

            if(noteData != null) {
                Name.setText(noteData.getName());
                describe.setText(noteData.getDescribe());
                date.setText(noteData.getDate().toString());
            }
        }

    }
}