package it.dawidwojdyla.myfirstdemoapp.api;

import org.json.JSONException;
import org.json.JSONObject;

import it.dawidwojdyla.myfirstdemoapp.Constants;
import it.dawidwojdyla.myfirstdemoapp.MainActivity;

public class DataBaseApiManager {

    private final MainActivity mainActivity;

    public DataBaseApiManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getLastInsertedData() {
        try {
            exchangeJsonWithApi(getRequestUrl(
                    Constants.GET_DATA_ACTION), prepareAuthorizationJson(), this::showGetDataResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            mainActivity.setMessageTextView(Constants.GET_DATA_ERROR_MESSAGE);
        }
    }

    public void sendDataToApi(String nickname) {
        try {
            exchangeJsonWithApi(getRequestUrl(Constants.SEND_DATA_ACTION),
                    prepareSendNicknameJson(nickname), this::showSendDataStatus);
        } catch (JSONException e) {
            e.printStackTrace();
            mainActivity.showToast(Constants.SEND_DATA_ERROR_MESSAGE);
        }
    }

    private void exchangeJsonWithApi(String url, JSONObject json, HandleJsonResponseInterface handler) {
        Thread thread = new Thread(() -> {
            JSONObject response = new HttpJsonExchanger().exchangeJsonWithApi(url, json);
            handler.handleResponse(response);
        });
        thread.start();
    }

    private String getRequestUrl(String action) {
        return Constants.API_HOST + "?action=" + action;
    }

    private void showGetDataResponse(JSONObject response) {
        String message;
        if (response == null || response.has("error")) {
            message = Constants.GET_DATA_ERROR_MESSAGE;
        } else {
            message = response.optString("nickname") + " (" + response.optString("date") + ")";
        }
        mainActivity.setMessageTextView(message);
    }

    private JSONObject prepareSendNicknameJson(String nickname) throws JSONException {
        return prepareAuthorizationJson().put("nickname", nickname);
    }

    private JSONObject prepareAuthorizationJson() throws JSONException {
        return new JSONObject()
                .put("username", Constants.USERNAME)
                .put("password", Constants.PASSWORD);
    }

    private void showSendDataStatus(JSONObject response) {
        if (response == null || response.has("error")) {
            mainActivity.showToast(Constants.SEND_DATA_ERROR_MESSAGE);
        } else {
            mainActivity.showToast(Constants.SEND_DATA_SUCCESS_MESSAGE);
            mainActivity.clearNicknameEditText();
        }
    }
}