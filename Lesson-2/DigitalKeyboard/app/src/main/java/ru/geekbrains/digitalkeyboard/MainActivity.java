package ru.geekbrains.digitalkeyboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CalculatorModel calculator;
    private TextView text;
    private final static String KeyCalculatorModel = "CalculatorModel";


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
        setContentView(R.layout.activity_main);


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

}