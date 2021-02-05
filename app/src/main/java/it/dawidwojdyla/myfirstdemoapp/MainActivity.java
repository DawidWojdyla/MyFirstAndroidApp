package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextView messageTextView;
    ApiRequestManager apiRequestManager = new ApiRequestManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        //Handler handler = new Handler(Looper.getMainLooper());

        Button showMainWindowButton = findViewById(R.id.btn_next);



        showMainWindowButton.setOnClickListener(l -> {
            manageMainWindow();
            makeRequestInThread(apiRequestManager);

        });
    }

    private void makeRequestInThread(ApiRequestManager apiRequestManager) {
        Thread thread = new Thread(() -> {
            try {
                JSONObject jsonObject = buildCredentialsJson();
                System.out.println("json przed: " + jsonObject.toString());
                JSONObject jsonResponse = apiRequestManager.exchangeJsonWithApi(
                        Constants.API_HOST + "?action=get_last_inserted_data", jsonObject.toString());

                showMessage(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void showMessage(JSONObject response) {
        String message;
        if (response == null || response.has("error") ) {
            //runOnUiThread(() -> showMessage(response));
            message = "Server error";
        } else {
            message = response.optString("nick") + " (" + response.optString("date") + ")";
        }
        messageTextView.setText(message);
    }

    private JSONObject buildCredentialsJson() throws JSONException {
        return new JSONObject().put("username", Constants.USERNAME).put("password", Constants.PASSWORD);
    }

    private void manageMainWindow() {

        setContentView(R.layout.layout_main);

        Button sendButton = findViewById(R.id.btn_send);
        messageTextView = findViewById(R.id.tv_message);

        sendButton.setOnClickListener(l -> {sendMessage();
            makeRequestInThread(apiRequestManager);
        });
    }

    private void sendMessage() {
        Toast.makeText(MainActivity.this, "SENDING MESSAGE", Toast.LENGTH_SHORT).show();

    }
}
