package it.dawidwojdyla.myfirstdemoapp.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import it.dawidwojdyla.myfirstdemoapp.Constants;


public class HttpJsonExchanger {

    public JSONObject exchangeJsonWithApi(String url, JSONObject json) {
        for (int i = 0; i <= Constants.HTTP_REQUEST_CONNECT_RETRIES_NUMBER; i++) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                connection.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                return new JSONObject(response.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}