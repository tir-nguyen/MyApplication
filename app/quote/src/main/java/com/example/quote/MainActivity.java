package com.example.quote;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = findViewById(R.id.quote);
        quote.setText("Quote đang lên xin từ từ ...");

        QuoteAPI quoteAPI = new QuoteAPI(this);
        quoteAPI.start();
    }
}