package com.example.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MiCliente {

    String apiKey = "af2d9f7f5ee44c18bf0195202261904";
    OkHttpClient client = new OkHttpClient();

    public ArrayList<Weather> getElements() {
        String urlForecast = "https://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=Mexico+City&days=3&aqi=no";

        Request request = new Request.Builder()
                .url(urlForecast)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ArrayList<Weather> elementos = new ArrayList<>();

            String bodyString = response.body().string();
            Log.d("API_RESPONSE", bodyString);

            JSONObject jsonObject = new JSONObject(bodyString);

            if (jsonObject.has("error")) {
                String errorMsg = jsonObject.getJSONObject("error").getString("message");
                throw new RuntimeException("API error: " + errorMsg);
            }

            String city = jsonObject.getJSONObject("location").getString("name");

            JSONArray forecastDays = jsonObject
                    .getJSONObject("forecast")
                    .getJSONArray("forecastday");

            for (int i = 0; i < forecastDays.length(); i++){
                JSONObject dayObj = forecastDays.getJSONObject(i);
                JSONObject day = dayObj.getJSONObject("day");

                String date = dayObj.getString("date");
                String photo = "https:" + day.getJSONObject("condition").getString("icon");
                double celsius = day.getDouble("maxtemp_c");
                double fahrenheit = day.getDouble("maxtemp_f");

                Weather weather = new Weather(city, date, photo, celsius, fahrenheit);
                elementos.add(weather);
            }

            return elementos;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}