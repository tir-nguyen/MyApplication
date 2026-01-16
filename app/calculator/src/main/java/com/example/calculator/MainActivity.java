package com.example.calculator;

import org.mariuszgromada.math.mxparser.Expression;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private boolean isOpenParentheses = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculator);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewResult = findViewById(R.id.textViewResult);
        setButtonClickListeners();
    }
    private void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "=":
                calculateResult();
                break;
            case "()":
                handleParentheses();
                break;
            case "⌫":
                removeLastInput();
                break;
            case "AC":
                clearInput();
                break;
            default:
                appendInput(buttonText);
                break;
        }
    }
    private void setButtonClickListeners() {
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonDot, R.id.buttonAC,
                R.id.buttonAdd, R.id.buttonSubtract,
                R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonPercent, R.id.buttonParentheses,
                R.id.buttonEqual, R.id.buttonBack
        };
        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(view -> onButtonClick(view));
        }
    }
    private void appendInput(String input) {
        textViewResult.setText(textViewResult.getText().toString() + input);
    }

    private void removeLastInput() {

        String s = textViewResult.getText().toString();
        if (s.length() > 0) {
            textViewResult.setText(s.substring(0, s.length() - 1));
        }
    }
    private void clearInput() {
        textViewResult.setText("");
    }
    private void handleParentheses() {
        if (isOpenParentheses) {
            appendInput(")");
            isOpenParentheses = false;
        } else {
            appendInput("(");
            isOpenParentheses = true;
        }
    }
    private void calculateResult() {
        try {
            String expression = textViewResult.getText().toString();
            Expression expressionEval = new Expression(expression);
            double result = expressionEval.calculate();
            textViewResult.setText(String.valueOf(result));
        } catch (Exception e) {
            textViewResult.setText("Error");
        }
    }

}
