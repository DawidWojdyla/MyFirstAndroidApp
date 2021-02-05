package it.dawidwojdyla.myfirstdemoapp;

import org.json.JSONException;
import org.json.JSONObject;

public class DataBaseApiManager {

    private final MainActivity mainActivity;

    public DataBaseApiManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getLastInsertedData() {
        Thread thread = new Thread(() -> {
            try {
                JSONObject response = new HttpJsonExchanger().exchangeJsonWithApi(
                        getRequestUrl(Constants.GET_DATA_ACTION), addCredentialsToJson(new JSONObject()).toString());
                showGetDataResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void sendDataToApi(String nickname) {
        Thread thread = new Thread(() -> {
            try {
                JSONObject response = new HttpJsonExchanger().exchangeJsonWithApi(
                        getRequestUrl(Constants.SEND_DATA_ACTION),
                        addCredentialsToJson(new JSONObject()).put("nickname", nickname).toString());
                showSendDataStatus(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private String getRequestUrl(String action) {
        return Constants.API_HOST + "?action=" + action;
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
        mainActivity.setMessageTextView(message);
    }

    private JSONObject addCredentialsToJson(JSONObject json) throws JSONException {
        return json
                .put("username", Constants.USERNAME)
                .put("password", Constants.PASSWORD);
    }

    private void showSendDataStatus(JSONObject response) {
        if (response == null || response.has("error")) {
            mainActivity.showSendDataStatusMessage(Constants.SEND_DATA_ERROR_MESSAGE);
        } else {
            mainActivity.showSendDataStatusMessage(Constants.SEND_DATA_SUCCESS_MESSAGE);
        }
    }
}