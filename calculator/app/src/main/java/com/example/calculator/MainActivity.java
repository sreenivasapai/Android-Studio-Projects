package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView display;

    double firstNumber = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2,
                R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8,
                R.id.btn9
        };

        for (int id : numberButtons) {

            Button button = findViewById(id);

            button.setOnClickListener(v -> {

                String number = button.getText().toString();

                if (display.getText().toString().equals("0")) {
                    display.setText(number);
                } else {
                    display.append(number);
                }
            });
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));

        findViewById(R.id.btnSubtract)
                .setOnClickListener(v -> setOperator("-"));

        findViewById(R.id.btnMultiply)
                .setOnClickListener(v -> setOperator("*"));

        findViewById(R.id.btnDivide)
                .setOnClickListener(v -> setOperator("/"));

        findViewById(R.id.btnEqual)
                .setOnClickListener(v -> calculate());

        findViewById(R.id.btnClear)
                .setOnClickListener(v -> clear());

        findViewById(R.id.btnDot)
                .setOnClickListener(v -> {

                    if (!display.getText().toString().contains(".")) {
                        display.append(".");
                    }
                });
    }

    private void setOperator(String op) {

        firstNumber = Double.parseDouble(
                display.getText().toString()
        );

        operator = op;

        display.setText("0");
    }

    private void calculate() {

        double secondNumber = Double.parseDouble(
                display.getText().toString()
        );

        double result = 0;

        switch (operator) {

            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "*":
                result = firstNumber * secondNumber;
                break;

            case "/":

                if (secondNumber == 0) {
                    display.setText("Error");
                    return;
                }

                result = firstNumber / secondNumber;
                break;
        }

        display.setText(String.valueOf(result));
    }

    private void clear() {

        display.setText("0");

        firstNumber = 0;

        operator = "";
    }
}