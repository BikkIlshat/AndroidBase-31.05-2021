package ru.geekbrains.notesjournal;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void addFragment(Fragment fragment, boolean useBackStack){


// Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// Удалить видимый фрагмент
        fragmentTransaction.replace(R.id.list_of_notes_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);


        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
}
