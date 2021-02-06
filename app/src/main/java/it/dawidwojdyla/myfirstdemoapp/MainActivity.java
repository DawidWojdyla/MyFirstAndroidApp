package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import it.dawidwojdyla.myfirstdemoapp.api.DataBaseApiManager;


public class MainActivity extends AppCompatActivity {

    TextView messageTextView;
    EditText nicknameEditText;

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
        nicknameEditText = findViewById(R.id.et_nickname);
        Button sendButton = findViewById(R.id.btn_send);
        DataBaseApiManager dbManager = new DataBaseApiManager(this);
        dbManager.getLastInsertedData();
        sendButton.setOnClickListener(l -> sendNicknameIfValid(nicknameEditText.getText().toString(), dbManager));
    }

    private void sendNicknameIfValid(String nickname, DataBaseApiManager dbManager) {
        if (nickname.length() < Constants.MIN_NICKNAME_LENGTH || nickname.length() > Constants.MAX_NICKNAME_LENGTH ) {
            showToast(Constants.NICKNAME_LENGTH_ERROR_MESSAGE);
        } else {
            dbManager.sendDataToApi(nickname);
        }
    }

    public void setMessageTextView(String message) {
        runOnUiThread(() -> messageTextView.setText(message));
    }

    public void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }
    public void clearNicknameEditText() {
        runOnUiThread(() -> nicknameEditText.setText(""));
    }
}
