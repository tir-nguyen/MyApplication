package com.example.quote;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuoteAPI extends Thread {

    MainActivity activity;

    public QuoteAPI(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            String apiUrl = "https://dummyjson.com/quotes/random";

            URL url = new URL(apiUrl);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());

            String quote = jsonObject.getString("quote");
            String author = jsonObject.getString("author");

            String result = "\"" + quote + "\"\n\n— " + author;

            activity.runOnUiThread(() -> {
                activity.quote.setText(result);
            });

        } catch (Exception e) {
            e.printStackTrace();

            activity.runOnUiThread(() -> {
                activity.quote.setText("Lỗi kết nối đến API");
            });
        }
    }
}