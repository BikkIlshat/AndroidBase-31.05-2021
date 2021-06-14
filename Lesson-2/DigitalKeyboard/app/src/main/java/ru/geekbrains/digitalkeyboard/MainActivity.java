package ru.geekbrains.digitalkeyboard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import static ru.geekbrains.digitalkeyboard.Const.AppTheme;
import static ru.geekbrains.digitalkeyboard.Const.AppThemeCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.AppThemeLightCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.MyCoolCodeStyle;
import static ru.geekbrains.digitalkeyboard.Const.NameSharedPreference;
public class MainActivity extends AppCompatActivity implements Constants {

    private CalculatorModel calculator;
    private TextView text;
    private final static String KeyCalculatorModel = "CalculatorModel";
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;


    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if( result.getResultCode() == Activity.RESULT_OK) {
                    recreate();
                }
            });

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(KeyCalculatorModel, calculator);
    }

    // Восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = instanceState.getParcelable(KeyCalculatorModel);
        setText();

    }

    private void setText() { text.setText(calculator.getText()); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme());
        setContentView(R.layout.activity_main);
        initView();




        int [] numberIds = new int [] {
                R.id.zero,
                R.id.one,
                R.id.two,
                R.id.three,
                R.id.four,
                R.id.five,
                R.id.six,
                R.id.seven,
                R.id.eight,
                R.id.nine,
                R.id.button_point

        };

        int [] actionsIds = new int[] {
                R.id.button_equals,
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_division,



        };

        text = findViewById(R.id.textView);

        calculator = new CalculatorModel();

// Передаём событие нажатия кнопки внутрь калькулятора (обработчик нажатия OnClickListener на кнопку )
        View.OnClickListener numberButtonClickListener = view -> {
            calculator.onNumPressed(view.getId());
            text.setText(calculator.getText()); // обнавляем текстовое поле и из кальк. получаем текс
        };


        View.OnClickListener actionButtonOnclickListener = view -> {
            calculator.onActionPressed(view.getId());
            text.setText(calculator.getText());
        };

        for (int i = 0; i < numberIds.length; i++) {
            findViewById(numberIds[i]).setOnClickListener(numberButtonClickListener);
        }

        for (int i = 0; i < actionsIds.length; i++) {
            findViewById(actionsIds[i]).setOnClickListener(actionButtonOnclickListener);
        }

        findViewById(R.id.reset).setOnClickListener(view -> {
            calculator.reset();
            text.setText(calculator.getText());
        });




    }


    private int getAppTheme() {
        return codeStyleToStyleId(getCodeStyle(R.style.AppThemeLight));
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

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle){
// Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
//Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }



    private void initView(){

        ImageButton btnSettings = findViewById(R.id.imageButton);
        btnSettings.setOnClickListener(v -> {
            // Чтобы стартовать активити, надо подготовить интент
            // В данном случае это будет явный интент, поскольку здесь
            //  передаётся класс активити
            Intent runSettings = new Intent(MainActivity.this,SettingsActivity.class);
            // Метод стартует активити, указанную в интенте
//            startActivity(runSettings);
//           startActivityForResult(runSettings, KEY_REQUEST_SETTINGS);
            activityLauncher.launch(runSettings);
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == KEY_REQUEST_SETTINGS) {
//            recreate();
//        }
//    }



    }