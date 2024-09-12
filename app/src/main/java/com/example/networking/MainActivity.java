package com.example.networking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTextView();
            }
        });
    }

    private void updateTextView() {

        // Make the network call here and update the TextView with the response

        // we can't directly make call on the network on main thread
        // because for any time consuming task, if we make call on main thread,
        // then for that time, screen will be frozen/slow/unresponsive
        // so we will make async call so creating class like below


        NetworkTask networkTask = new NetworkTask();
        networkTask.execute("https://www.google.com", "https://www.github.com");
    }

    // 1st parameter is String because we will provide URL which is in String format
    // 2nd parameter is Void because we don't need/show progress update via API Call
    // 3rd parameter is String because we will get response in String format
    class NetworkTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            try {
                URL url = new URL(stringUrl);
                url.openConnection();

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                // this delimiter allows me to read whole input stream in one go
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    String s = scanner.next();
                    return s;
                }
            } catch (MalformedURLException e) {
                Log.d("NetworkTask", "doInBackground: Not Valid URL");
                throw new RuntimeException(e);
            } catch (IOException e) {
                Log.d("NetworkTask", "doInBackground: Not able to make Network call");
                throw new RuntimeException(e);
            }

            return "Failed to load";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = findViewById(R.id.textView);
            textView.setText(s);
        }
    }
}
