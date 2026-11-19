package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView tvExpression, tvResult;

    private String input = "";
    private Double first = null;
    private String op = null;
    private boolean justEvaluated = false;
    private String lastExpression = "";

    private static final int MAX_DIGITS = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);

        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };
        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v ->
                    onDigit(((Button) v).getText().toString()));
        }

        int[] operatorIds = {
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        };
        for (int id : operatorIds) {
            findViewById(id).setOnClickListener(v ->
                    onOperator(((Button) v).getText().toString()));
        }

        findViewById(R.id.btnEquals).setOnClickListener(v -> onEquals());
        findViewById(R.id.btnClear).setOnClickListener(v -> onClear());
        findViewById(R.id.btnBack).setOnClickListener(v -> onBackspace());

        updateDisplay();
    }

    private void onDigit(String digit) {
        if (justEvaluated) {
            resetState();
        }
        if (input.equals("0")) {
            input = digit;
        } else if (input.replace(".", "").replace("-", "").length() < MainActivity.MAX_DIGITS) {
            input += digit;
        }
        updateDisplay();
    }

    private void onOperator(String newOp) {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            if (first == null) {
                first = value;
            } else if (op != null) {
                if (op.equals("÷") && value == 0) {
                    showError();
                    return;
                }
                first = calculate(first, value, op);
            }
            input = "";
        } else if (first == null) {
            return;
        }
        op = newOp;
        justEvaluated = false;
        updateDisplay();
    }

    private void onEquals() {
        if (first == null || op == null || input.isEmpty()) return;

        double second = Double.parseDouble(input);
        if (op.equals("÷") && second == 0) {
            showError();
            return;
        }

        double result = calculate(first, second, op);
        lastExpression = format(first) + " " + op + " " + format(second) + " =";
        first = result;
        op = null;
        input = "";
        justEvaluated = true;
        updateDisplay();
    }

    private void onClear() {
        resetState();
        updateDisplay();
    }

    private void onBackspace() {
        if (justEvaluated) {
            input = format(first);
            first = null;
            op = null;
            lastExpression = "";
            justEvaluated = false;
        } else if (input.isEmpty()) {
            if (op != null && first != null) {
                input = format(first);
                first = null;
                op = null;
            }
            updateDisplay();
            return;
        }

        if (!input.isEmpty()) {
            input = input.substring(0, input.length() - 1);
        }
        if (input.equals("-")) {
            input = "";
        }
        updateDisplay();
    }

    private double calculate(double a, double b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "−": return a - b;
            case "×": return a * b;
            case "÷": return a / b;
            default:  return b;
        }
    }

    private void resetState() {
        input = "";
        first = null;
        op = null;
        justEvaluated = false;
        lastExpression = "";
    }

    private void showError() {
        resetState();
        tvExpression.setText("");
        tvResult.setText("Can't divide by 0");
    }

    private void updateDisplay() {
        if (justEvaluated) {
            tvExpression.setText(lastExpression);
        } else if (first != null && op != null) {
            tvExpression.setText(format(first) + " " + op + (input.isEmpty() ? "" : " " + input));
        } else {
            tvExpression.setText("");
        }

        if (!input.isEmpty()) {
            tvResult.setText(input);
        } else if (first != null) {
            tvResult.setText(format(first));
        } else {
            tvResult.setText("0");
        }
    }



    private String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return BigDecimal.valueOf(value)
                .setScale(8, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }
}



