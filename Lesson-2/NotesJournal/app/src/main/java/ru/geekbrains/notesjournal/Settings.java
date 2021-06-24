package ru.geekbrains.notesjournal;

//  В этом классе храним свои установки

public class Settings {
    // Наши ключи которые будут храниться в префeренсах:
    public static final String SHARED_PREFERENCE_NAME = "FragmentNavigation";
    public static final String IS_BACK_STACK_USED = "UseBackStack";
    public static final String IS_ADD_FRAGMENT_USED = "UseAddFragment";
    public static final String IS_BACK_AS_REMOVE_FRAGMENT = "BackAsRemove";
    public static final String IS_DELETE_FRAGMENT_BEFORE_ADD = "DeleteFragmentBeforeAdd";

    // а это то что мы достаём из этих префов
    public static boolean isBackStack;
    public static boolean isAddFragment;
    public static boolean isBackAsRemove;
    public static boolean isDeleteBeforeAdd;
}