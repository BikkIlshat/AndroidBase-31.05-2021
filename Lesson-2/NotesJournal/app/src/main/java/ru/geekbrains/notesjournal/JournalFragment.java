package ru.geekbrains.notesjournal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class JournalFragment extends Fragment {

    public JournalFragment() {

    }

     // inflater.inflate -  работая с с xml можем указать его в методе что бы он был в виде объекта.
     // Превращает наш  fragment_journal в объект, в данном случае будет объектом  Java-вского элемнта View
    // и который обратно передадим системе  - и вот этот View будет являться фрагментом.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false);
    }

    // Обратите внимание, что в ниже приведенном  методе onViewCreated() вызывается метод initList(), в котором
    //создаётся список динамических элементов из массива строк, описанного в ресурсах.


    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    // Ниже инициилизируем список:
    @Override // когда создали  метод  public View onCreateView - этот  же самый View прилетает в параметы нижеприведенного метода  (@NonNull View view, @Nullable Bundle savedInstanceState)
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // Обязательно вызываем какую-нибудь передыдущую функцию. (onViewCreated - это базовая функция)

    // что бы проинициилизировать список создаём последовательность:

        initlist((LinearLayout) view);
    }

    private void initlist(LinearLayout view) {
        // LinearLayout - потому что наш фрагмент создали на макете LinearLayout
        String[] notes = getResources().getStringArray(R.array.notes); // получаем наш спискок notes (наши заметки) getResources - получить ресурсы; getStringArray - получить массив строк


        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент

        for(int i=0; i < notes.length; i++){
            String note = notes[i];
            TextView tv = new TextView(getContext()); // У нас есть TextView -  это как бы показатель того что мы все текстовые элементы можем создавать сами из кода и как-то куда-то добавлять, в данном случае здесь мы их создали
            tv.setText(note);
            tv.setTextSize(30);
            view.addView(tv); // добавляем командой addView() в наш  layoutView
            final int index = i; // законстантили
            tv.setOnClickListener(v -> {
                showPortDescriptionOfNotes(index);
            });
        }
    }

    // как нам этот фрагмент вызывать ? перейдем в -> activity_main

    // Делаем обработку нажатия на наши заметки

    // Показываем в портретной  ориентации
    private void showPortDescriptionOfNotes(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        intent.putExtra(DescriptionOfNotes.ARG_INDEX, index);
        startActivity(intent);

        // как всё это сделали должны написать полсе view.addView(tv);
//        tv.setOnClickListener(v -> {
//            final int index = i;
//            showPortDescriptionOfNotes(index);
//        });

    }






}