
package com.example.calculaotadvanced;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView display, history;

    double firstNumber = 0;
    String operator = "";
    boolean waitingForNumber = true;
    boolean resultShown = false;

    ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        history = findViewById(R.id.history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();

        if (display.getText().toString().equals("Error")
                && !value.equals("AC") && !value.equals("HIST")) {
            clearCalculator();
        }

        switch (value) {
            case "AC":
                clearCalculator();
                break;

            case "DEL":
                deleteLast();
                break;

            case "+":
            case "−":
            case "×":
            case "÷":
            case "^":
                setOperator(value);
                break;

            case "=":
                calculate();
                break;

            case ".":
                addDecimal();
                break;

            case "±":
                changeSign();
                break;

            case "%":
                percentage();
                break;

            case "x²":
                square();
                break;

            case "√x":
                squareRoot();
                break;

            case "HIST":
                showHistory();
                break;

            default:
                addNumber(value);
                break;
        }
    }

    private void addNumber(String number) {
        String current = display.getText().toString();

        if (waitingForNumber || resultShown || current.equals("0")) {
            display.setText(number);
            waitingForNumber = false;
            resultShown = false;
        } else {
            display.append(number);
        }
    }

    private void addDecimal() {
        String current = display.getText().toString();

        if (waitingForNumber || resultShown) {
            display.setText("0.");
            waitingForNumber = false;
            resultShown = false;
        } else if (!current.contains(".")) {
            display.append(".");
        }
    }

    private void setOperator(String op) {
        try {
            if (!operator.isEmpty() && !waitingForNumber) {
                calculate();

                if (display.getText().toString().equals("Error")) {
                    return;
                }
            }

            firstNumber = Double.parseDouble(
                    display.getText().toString());

            operator = op;
            waitingForNumber = true;
            resultShown = false;

            history.setText(format(firstNumber) + " " + op);

        } catch (Exception e) {
            showError();
        }
    }

    private void calculate() {
        if (operator.isEmpty() || waitingForNumber) {
            return;
        }

        try {
            double secondNumber = Double.parseDouble(
                    display.getText().toString());

            double result;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;

                case "−":
                    result = firstNumber - secondNumber;
                    break;

                case "×":
                    result = firstNumber * secondNumber;
                    break;

                case "÷":
                    if (secondNumber == 0) {
                        showError();
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;

                case "^":
                    result = Math.pow(firstNumber, secondNumber);
                    break;

                default:
                    return;
            }

            if (Double.isNaN(result) || Double.isInfinite(result)) {
                showError();
                return;
            }

            String expression = format(firstNumber) + " "
                    + operator + " " + format(secondNumber);

            String answer = format(result);

            history.setText(expression + " = " + answer);
            historyList.add(expression + " = " + answer);

            display.setText(answer);

            firstNumber = result;
            operator = "";
            waitingForNumber = true;
            resultShown = true;

        } catch (Exception e) {
            showError();
        }
    }

    private void percentage() {
        try {
            double number = Double.parseDouble(
                    display.getText().toString());

            double result = number / 100;

            history.setText(format(number) + "% = " + format(result));
            display.setText(format(result));

            waitingForNumber = true;
            resultShown = true;

        } catch (Exception e) {
            showError();
        }
    }

    private void square() {
        try {
            double number = Double.parseDouble(
                    display.getText().toString());

            double result = number * number;

            history.setText(format(number) + "² = " + format(result));
            display.setText(format(result));

            waitingForNumber = true;
            resultShown = true;

        } catch (Exception e) {
            showError();
        }
    }

    private void squareRoot() {
        try {
            double number = Double.parseDouble(
                    display.getText().toString());

            if (number < 0) {
                showError();
                return;
            }

            double result = Math.sqrt(number);

            history.setText("√" + format(number) + " = " + format(result));
            display.setText(format(result));

            waitingForNumber = true;
            resultShown = true;

        } catch (Exception e) {
            showError();
        }
    }

    private void changeSign() {
        String current = display.getText().toString();

        if (current.equals("0") || current.equals("Error")) {
            return;
        }

        if (current.startsWith("-")) {
            display.setText(current.substring(1));
        } else {
            display.setText("-" + current);
        }

        waitingForNumber = false;
        resultShown = false;
    }

    private void deleteLast() {
        String current = display.getText().toString();

        if (resultShown || current.equals("Error")) {
            display.setText("0");
            waitingForNumber = true;
            resultShown = false;
            return;
        }

        if (current.length() <= 1
                || (current.length() == 2 && current.startsWith("-"))) {
            display.setText("0");
            waitingForNumber = true;
        } else {
            display.setText(current.substring(0, current.length() - 1));
        }
    }

    private void clearCalculator() {
        display.setText("0");
        history.setText("Simple Advanced Calculator");

        firstNumber = 0;
        operator = "";
        waitingForNumber = true;
        resultShown = false;
    }

    private String format(double number) {
        if (number == 0) {
            return "0";
        }

        if (Math.abs(number) < 1e15
                && number == Math.rint(number)) {
            return String.valueOf((long) number);
        }

        return new DecimalFormat("0.##########").format(number);
    }

    private void showError() {
        display.setText("Error");
        firstNumber = 0;
        operator = "";
        waitingForNumber = true;
        resultShown = false;

        Toast.makeText(this, "Invalid calculation",
                Toast.LENGTH_SHORT).show();
    }

    private void showHistory() {
        if (historyList.isEmpty()) {
            Toast.makeText(this, "No calculation history",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder items = new StringBuilder();

        for (int i = historyList.size() - 1; i >= 0; i--) {
            items.append(historyList.get(i)).append("\n\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Calculation History")
                .setMessage(items.toString())
                .setPositiveButton("Close", null)
                .setNegativeButton("Clear History", (dialog, which) -> {
                    historyList.clear();
                    history.setText("History cleared");
                })
                .show();
    }
}
