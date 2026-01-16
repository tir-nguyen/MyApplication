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
    private TextView textViewResult; // TextView hiển thị kết quả hiện tại (VD: 2)
    private TextView textViewHistory; // TextView hiển thị phép tính gần nhất (VD: 1+1)
    private boolean isOpenParentheses = false;      // Biến kiểm soát trạng thái ngoặc ()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // Ánh xạ TextView từ layout XML
        textViewResult = findViewById(R.id.textViewResult);
        textViewHistory = findViewById(R.id.textViewHistory);

        setButtonClickListeners();  // Gán sự kiện click cho tất cả các nút
    }
    //Hàm xử lý sự kiện khi bấm vào bất kỳ nút nào
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
//    Gán OnClickListener cho toàn bộ các nút của máy tính
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
            button.setOnClickListener(this::onButtonClick);
        }
    }
//    Thêm ký tự (số / toán tử) vào TextView kết quả
    private void appendInput(String input) {
        textViewResult.setText(textViewResult.getText().toString() + input);
    }
//    Xóa ký tự cuối cùng (nút Backspace ⌫)
private void removeLastInput() {
        String s = textViewResult.getText().toString();
        if (s.length() > 0) {
            textViewResult.setText(s.substring(0, s.length() - 1));
        }
    }
//    Xóa toàn bộ dữ liệu (AC)
    private void clearInput() {
        textViewResult.setText("");
        textViewHistory.setText("");
//        isOpenParentheses = false;
    }
//    Xử lý nút ngoặc (), tự động mở hoặc đóng ngoặc
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
            textViewHistory.setText(expression + " = " + result);
            textViewResult.setText(String.valueOf(result));
            isOpenParentheses = false;
        } catch (Exception e) {
            textViewResult.setText("Error");
        }
    }
}
