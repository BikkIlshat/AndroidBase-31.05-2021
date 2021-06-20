package ru.geekbrains.notesjournal;



// Фрагмент настроек чтоб, как-то регулировать  смену фргаментов между собой

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    /*
onCreateView — формирование компонента для отображения. Возвращает вид фрагмента. Может возвращать null для невизуальных компонентов.
Сюда передаются данные класса Bundle о последнем состоянии фрагмента, а также контейнер активности, куда будет подключаться фрагмент
 и «надуватель» разметки. - этот класс в офрагменте обязательный
 */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
        // вот этот view  передаём системе что бы  система поняла с каким макетом этот фрагмент (в данном случае fragment_settings) будет работать
        // inflater.inflate(R.layout.fragment_settings, container, false) - на основе этих данных создаём view который будет являться самы главным Рутовым элементом
        //для этого фрагмента
    }

/*
onViewCreated — вызывается, когда вид сформирован. Сюда передаётся сформированный вид и данные класса Bundle о последнем состоянии фрагмента.
Используется для окончательной инициализации вида перед восстановлением сохранённого состояния. В этой точке вид ещё не прикреплён к фрагменту.
 */


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    // инициализация моих  view
    private void initView(View view) {
        initSwitchUseBackStack(view);
        initRadioButtonAdd(view);
        initRadioButtonReplace(view);
        initSwitchBackAsRemove(view);
        initSwitchDeleteBeforeAdd(view); //
    }

    private void initSwitchDeleteBeforeAdd(View view) {
        SwitchCompat switchDeleteBeforeAdd = view.findViewById(R.id.switchDeleteBeforeAdd);
        switchDeleteBeforeAdd.setChecked(Settings.isDeleteBeforeAdd);
        switchDeleteBeforeAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            /*
              onCheckedChanged - вызывается всегда при смене положения кнопки Switch on/of
               boolean isChecked - который будет отрабатывать
               isChecked - будем присваевыть нашим установкам (Settings)
               isChecked - on
               !isChecked - of
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isDeleteBeforeAdd = isChecked; //on
                writeSettings(); // - это сохранение настроек
            }
        });
    }

    private void initSwitchBackAsRemove(View view) {
        SwitchCompat switchBackAsRemove = view.findViewById(R.id.switchBackAsRemove);
        switchBackAsRemove.setChecked(Settings.isBackAsRemove);
        switchBackAsRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /*
              onCheckedChanged - вызывается всегда при смене положения кнопки Switch on/of
               boolean isChecked - который будет отрабатывать
               isChecked - будем присваевыть нашим установкам (Settings)
               isChecked - on
               !isChecked - of
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isBackAsRemove = isChecked; // on
                writeSettings(); // сохранение
            }
        });
    }

    private void initRadioButtonReplace(View view) {
        RadioButton radioButtonReplace = view.findViewById(R.id.radioButtonReplace);
        radioButtonReplace.setChecked(!Settings.isAddFragment);
        radioButtonReplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /*
              onCheckedChanged - вызывается всегда при смене положения кнопки Switch on/of
               boolean isChecked - который будет отрабатывать
               isChecked - будем присваевыть нашим установкам (Settings)
             */

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isAddFragment = !isChecked; // of
                writeSettings(); // сохранение
            }
        });
    }

    private void initRadioButtonAdd(View view) {
        RadioButton radioButtonAdd = view.findViewById(R.id.radioButtonAdd);
        radioButtonAdd.setChecked(Settings.isAddFragment);
        radioButtonAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /*
              onCheckedChanged - вызывается всегда при смене положения кнопки Switch on/of
               boolean isChecked - который будет отрабатывать
               isChecked - будем присваевыть нашим установкам (Settings)
             */

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isAddFragment = isChecked; // on
                writeSettings(); // сохранение
            }
        });
    }

    private void initSwitchUseBackStack(View view) {
        // Элемент пользовательского интерфейса — переключатель
        // По функционалу очень похож на CheckBox, но имеет другой дизайн
        SwitchCompat switchUseBackStack = view.findViewById(R.id.switchBackStack);
        switchUseBackStack.setChecked(Settings.isBackStack);
        switchUseBackStack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /*
              onCheckedChanged - вызывается всегда при смене положения кнопки Switch on/of
               boolean isChecked - который будет отрабатывать
               isChecked - будем присваевыть нашим установкам (Settings)
             */

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isBackStack = isChecked; // on
                writeSettings(); // сохранение
            }
        });
    }

    // Сохранение настроек приложения
    private void writeSettings(){
        // Специальный класс для хранения настроек
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor
        SharedPreferences.Editor editor = sharedPref.edit();
        // Задаём значения настроек
        editor.putBoolean(Settings.IS_BACK_STACK_USED, Settings.isBackStack);
        editor.putBoolean(Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment);
        editor.putBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, Settings.isBackAsRemove);
        editor.putBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, Settings.isDeleteBeforeAdd);
        // Сохраняем значения настроек
        editor.apply();
    }
}