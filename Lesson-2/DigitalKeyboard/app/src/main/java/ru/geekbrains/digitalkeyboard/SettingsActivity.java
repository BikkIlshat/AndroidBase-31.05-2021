package ru.geekbrains.digitalkeyboard;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;

import static ru.geekbrains.digitalkeyboard.Const.AppTheme;
import static ru.geekbrains.digitalkeyboard.Const.AppThemeCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.AppThemeLightCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.MyCoolCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.NameSharedPreference;


public class SettingsActivity extends AppCompatActivity implements Constants {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme());
        setContentView(R.layout.activity_settings);

        initThemeChooser();

        ImageButton btnReturn = findViewById(R.id.imageButton);
        btnReturn.setOnClickListener(v -> {
            // Метод finish() завершает активити
//            setResult(RESULT_OK);
            setResult(Activity.RESULT_OK);
            finish();
        });
    }

    private int getAppTheme() {
        return codeStyleToStyleId(getCodeStyle(R.style.AppThemeLight));
    }


    // Инициализация радиокнопок
    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMyCoolStyle), MyCoolCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLight), AppThemeLightCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLightDarkAction), AppThemeCodeStyle);
        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton)rg.getChildAt(getCodeStyle(MyCoolCodeStyle))).setChecked(true);
    }


// Все инициализации кнопок очень похожи, поэтому создадим метод для переиспользования
    private void initRadioButton(View button, final int codeStyle){
        button.setOnClickListener(v -> {
// сохраним настройки
            setAppTheme(codeStyle);
// пересоздадим активити, чтобы тема применилась
            recreate();
        });
    }



    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle){
// Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
//Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,MODE_PRIVATE);
// Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle){
        switch(codeStyle){
            case AppThemeCodeStyle:
                return R.style.AppTheme;
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            default:
                return R.style.MyCoolStyle;
        }
    }
}

