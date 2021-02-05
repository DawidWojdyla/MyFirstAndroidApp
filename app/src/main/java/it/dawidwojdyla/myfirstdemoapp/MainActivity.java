package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


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
        getLastInsertedData();
        setContentView(R.layout.layout_main);
        messageTextView = findViewById(R.id.tv_message);
        EditText nicknameEditText = findViewById(R.id.et_nickname);
        Button sendButton = findViewById(R.id.btn_send);

        sendButton.setOnClickListener(l -> sendDataToApi(nicknameEditText.getText().toString()));
    }

    private void getLastInsertedData() {
        Thread thread = new Thread(() -> {
            try {
                JSONObject response = new ApiRequestManager().exchangeJsonWithApi(
                        getRequestUrl(Constants.GET_DATA_ACTION), addCredentialsToJson(new JSONObject()).toString());
                showGetDataResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void sendDataToApi(String nickname) {

        Thread thread = new Thread(() -> {
            try {
                JSONObject response = new ApiRequestManager().exchangeJsonWithApi(
                        getRequestUrl(Constants.SEND_DATA_ACTION),
                        addCredentialsToJson(new JSONObject()).put("nickname", nickname).toString());
                showSendDataStatus(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void showGetDataResponse(JSONObject response) {
        String message;
        if (response == null) {
            message = "Server error";
        } else if (response.has("error")) {
            message = response.optString("error");
        } else {
            message = response.optString("nick") + " (" + response.optString("date") + ")";
        }
        messageTextView.setText(message);
    }

    private JSONObject addCredentialsToJson(JSONObject json) throws JSONException {
        return json
                .put("username", Constants.USERNAME)
                .put("password", Constants.PASSWORD);
    }

    private void showSendDataStatus(JSONObject response) {
        if (response == null || response.has("error")) {
            showSendDataStatusMessage(Constants.SEND_DATA_ERROR_MESSAGE);
        } else {
            showSendDataStatusMessage(Constants.SEND_DATA_SUCCESS_MESSAGE);
        }
    }

    private void showSendDataStatusMessage(final String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    private String getRequestUrl(String action) {
        return Constants.API_HOST + "?action=" + action;
    }
}
