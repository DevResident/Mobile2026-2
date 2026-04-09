package com.example.actividad7;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MiCliente {

    private String url = "XXX";
    OkHttpClient client = new OkHttpClient();

    public ArrayList<String> getElements() {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String respuesta =  response.body().string();

            ArrayList<String> elementos = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray array = jsonObject.getJSONArray("characters");
            for (int i = 0; i <= array.length(); i++){
                String elemento = array.getString(i);
                elementos.add(elemento);
            }
            return elementos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}