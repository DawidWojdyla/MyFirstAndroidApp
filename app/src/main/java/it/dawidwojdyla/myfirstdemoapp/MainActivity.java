package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        Button showMainWindowButton = findViewById(R.id.btn_next);
        showMainWindowButton.setOnClickListener(l -> manageMainWindow());
    }

    private void manageMainWindow() {
        setContentView(R.layout.layout_main);
        messageTextView = findViewById(R.id.tv_message);
        EditText nicknameEditText = findViewById(R.id.et_nickname);
        Button sendButton = findViewById(R.id.btn_send);
        DataBaseApiManager dbManager = new DataBaseApiManager(this);
        dbManager.getLastInsertedData();
        sendButton.setOnClickListener(l -> dbManager.sendDataToApi(nicknameEditText.getText().toString()));
    }

    public void setMessageTextView(String message) {
        messageTextView.setText(message);
    }

    public void showSendDataStatusMessage(final String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}
