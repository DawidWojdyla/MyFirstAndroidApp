package it.dawidwojdyla.myfirstdemoapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Dawid on 2021-01-16.
 */
public class ApiRequestManager {


    public JSONObject exchangeJsonWithApi(String url, String stringJson) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            byte[] bytesJson = stringJson.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytesJson, 0, bytesJson.length);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }

            return new JSONObject(response.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}