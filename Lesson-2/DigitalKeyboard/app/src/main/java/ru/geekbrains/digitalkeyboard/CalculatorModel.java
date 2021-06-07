package ru.geekbrains.digitalkeyboard;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorModel implements Parcelable {

    private int firstArg; // первый аргумет
    private int secondArg; // второй аргумент
    private int actionSelected ; // переменная которая хранит в себе выбранное дейстивие (плюс, минус, разделить, и.т.п)

    private State state; // это текущее состояние калькулятора. Показывает на каком этапе работы находится калькулятор.
    private final StringBuilder inputStr = new StringBuilder(); // накапливаются чилса когда мы нажимаем на кнопки




    protected CalculatorModel(Parcel in) {
        firstArg = in.readInt();
        secondArg = in.readInt();
        actionSelected = in.readInt();
    }

    public static final Creator<CalculatorModel> CREATOR = new Creator<CalculatorModel>() {
        @Override
        public CalculatorModel createFromParcel(Parcel in) {
            return new CalculatorModel(in);
        }

        @Override
        public CalculatorModel[] newArray(int size) {
            return new CalculatorModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(firstArg);
        dest.writeInt(secondArg);
        dest.writeInt(actionSelected);
    }

    // перечелсяем состояние через класс enum
    private enum State {
        firstArgInput, // калькулятор в процессе ввода первого аргумента
        secondArgInput, // калькулятор в процессе ввода второго аргумента
        operationSelected, // пользователь выбрал действие (+-/*)
        resultShow // калькулятор вычислил и показывает результат.
    }

    public CalculatorModel() {
        state = State.firstArgInput;
    }


    // обработчик нажатия цифровой кнопки
    @SuppressLint("NonConstantResourceId")
    public void onNumPressed(int buttonId) {

        if (state == State.resultShow) { // если мы показывали результаты операции
            state = State.firstArgInput; // то переходим опять к вводу второго аргумента
            inputStr.setLength(0); // если состояние  финальное и показывает результаты предыдущей операци то буфер очищается
        }

        if (state == State.operationSelected) {
            state = State.secondArgInput;
            inputStr.setLength(0);
        }

        if (inputStr.length() < 9) { // ограниечение на макс. длину (если есть 10 символов то ничего не добавляем )
            switch (buttonId) {
                case R.id.zero: // если нажата кнопка 0 (нажать ее можно но есть условие):
                    if (inputStr.length() != 0) { // 0 не может быть первым символом (дополнительная проверка)
                        inputStr.append("0");
                    }
                    break;
                case R.id.one:
                    inputStr.append("1");
                    break;
                case R.id.two:
                    inputStr.append("2");
                    break;
                case R.id.three:
                    inputStr.append("3");
                    break;
                case R.id.four:
                    inputStr.append("4");
                    break;
                case R.id.five:
                    inputStr.append("5");
                    break;
                case R.id.six:
                    inputStr.append("6");
                    break;
                case R.id.seven:
                    inputStr.append("7");
                    break;
                case R.id.eight:
                    inputStr.append("8");
                    break;
                case R.id.nine:
                    inputStr.append("9");
                    break;
                case R.id.button_point:
                    inputStr.append(".");
                    break;

            }
        }

    }

    // функция обработки кнопок которые представляют из себя действия (+-/*=)
    @SuppressLint("NonConstantResourceId")
    public void onActionPressed(int actionId) {
        if (actionId == R.id.button_equals && state == State.secondArgInput && inputStr.length() > 0) {
            secondArg = Integer.parseInt(inputStr.toString());
            state = State.resultShow;
            inputStr.setLength(0); // очищаем буфер строки
            switch (actionSelected) {
                case R.id.button_plus:
                    inputStr.append(firstArg + secondArg);
                    break;
                case R.id.button_minus:
                    inputStr.append(firstArg - secondArg);
                    break;
                case R.id.button_multiply:
                    inputStr.append(firstArg * secondArg);
                    break;
                case R.id.button_division:
                    inputStr.append(firstArg / secondArg);
                    break;
                    // верхняя часть относится к обработке равно, она срабабатывает только если мы находимся в ссотоянии ввода второго аргумента
            }

        } else if (inputStr.length() > 0 && state == State.firstArgInput && actionId != R.id.button_equals) {
            firstArg = Integer.parseInt(inputStr.toString());
            state = State.operationSelected;
            actionSelected = actionId;
        }
    }

    public String getText() { // метод позволяющий получить текст который можно будет отобразить в текстовом поле (1й аргумент, 2й аргумент и результат)
        StringBuilder str = new StringBuilder();
        switch (state) {
            default:
                return inputStr.toString();
            case operationSelected:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .toString();
            case secondArgInput:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(inputStr)
                        .toString();
            case resultShow:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(secondArg)
                        .append(" = ")
                        .append(inputStr.toString())
                        .toString();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private char getOperationChar() {
        switch (actionSelected) {
            case R.id.button_plus:
                return '+';
            case R.id.button_minus:
                return '-';
            case R.id.button_multiply:
                return '*';
            case R.id.button_division:
            default:return '/';

        }
    }

    public void reset() {
        state = State.firstArgInput;
        inputStr.setLength(0);
    }

}
