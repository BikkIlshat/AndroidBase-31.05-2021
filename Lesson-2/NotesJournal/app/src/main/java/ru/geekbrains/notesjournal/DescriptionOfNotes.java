package ru.geekbrains.notesjournal;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DescriptionOfNotes extends Fragment {


    public static final String ARG_INDEX = "index";


    private int index;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    // newInstance - метод который умеет создавать объект. Этот метод на на входе получает параметр  индекс элемента массива
    public static DescriptionOfNotes newInstance(int index) {
        DescriptionOfNotes fragment = new DescriptionOfNotes(); // создание

        //Передача парааметров
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);

        }
    }

    /*
    inflater.inflate -  работая с с xml можем указать его в методе что бы он был в виде объекта.
    Превращает наш  fragment_description_of_notes в объект, в данном случае будет объектом  Java-вского элемнта View
    и который обратно передадим системе  - и вот этот View будет являться фрагментом.
    Метод onCreateView во фрагменте — аналог метода setContentView в активити.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // подправляем код  return inflater.inflate(R.layout.fragment_description_of_notes, container, false); на ниже приведенный:

        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_description_of_notes, container, false);

        // Пишем нашу обработку:

        // Мы должны в нашем view найти нейкий элемент называющийся EditText
        AppCompatEditText editText = view.findViewById(R.id.editTextTextMultiLine);


        // Дальше должны получить из ресурсов наш массив
        String[] str = getResources().getStringArray(R.array.description_of_notes_editTextTextMultiLine);



        // Тут я не понимаю как дельше двигаться, что бы  нажимая на Заметку открывалась описание заметки
        int getIndex = str.getClass().toString().indexOf(index, -1);

        // Выбрать по индексу подходящий и  засэтать


        // и теперь это описание должны отправить  в AppCompatEditText которе называется editText


        return view;


    }

}