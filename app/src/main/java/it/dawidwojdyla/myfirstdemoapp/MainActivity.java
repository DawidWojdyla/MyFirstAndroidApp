package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        Button showMainWindowButton = findViewById(R.id.btn_next);

        //Handler handler = new Handler(Looper.getMainLooper());

        showMainWindowButton.setOnClickListener(l -> {
            manageMainWindow();
            makeRequestInThread();

        });
    }

    private void manageMainWindow() {

        setContentView(R.layout.layout_main);

        Button sendButton = findViewById(R.id.btn_send);
        messageTextView = findViewById(R.id.tv_message);

        sendButton.setOnClickListener(l -> {
            makeRequestInThread();
        });
    }

    private void makeRequestInThread() {
        Thread thread = new Thread(() -> {
            try {
                //Do some request and and show message

                showMessageToast("message");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


    private void showMessageToast(String message) {
        //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        messageTextView.setText(message);

    }
}
