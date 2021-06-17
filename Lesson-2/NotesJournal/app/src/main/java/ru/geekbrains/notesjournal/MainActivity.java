package ru.geekbrains.notesjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  делаем защиту (если ORIENTATION_LANDSCAPE - то нас это не интересует), если это условие будет не пройдено то будем выполнять метод finish()
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();// завершаем активити, не будем заходить никуда
            return;

        }

        // Передача данных в эту активити Обработка:
        if (savedInstanceState == null) {
            DescriptionOfNotesFragment details = new DescriptionOfNotesFragment(); // DescriptionOfNotes - это наш фрагмент
            details.setArguments(getIntent().getExtras());


            // Дальше этот фрагмент должны добавить на экран: тут я пошел в активити_мэйн и создал там fragment_container
            getSupportFragmentManager()
                    .beginTransaction()// Открываю транзакцию
                    .replace(R.id.fragment_container, details) // Замини мне в этом фрагменте fragment_container на details
                    .commit(); // Завершаем транзакцию

            /*
            В одной транзакции можно поменять несколько макетов
            Смысл такой чтобы програмно вставить фрагмент мы получаем  FragmentManager
            Потом окрываем транзакцию  beginTransaction говорим replace или add
            И завершаем транзакцию через commit().
             И фрагмент должен будет появиться на экране после этого.
             */
        }

            // Делаем обработку нажатия на наши заметки переходим в JournalFragment

    }





}