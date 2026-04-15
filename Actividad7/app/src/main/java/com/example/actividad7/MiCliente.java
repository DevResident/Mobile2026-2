package com.example.actividad7;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MiCliente {

    private String url = "https://function-fca-production-ab09.up.railway.app/";
    OkHttpClient client = new OkHttpClient();

    public ArrayList<Personaje> getElements() {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ArrayList<Personaje> elementos = new ArrayList<>();

            JSONArray array = new JSONArray(response.body().string());

            for (int i = 0; i < array.length(); i++){
                JSONObject elemento = array.getJSONObject(i);
                String name = elemento.getString("name");
                String description = elemento.getString("description");
                String photo = elemento.getString("photo");
                int attack = elemento.getInt("attack");
                int defense = elemento.getInt("defense");

                Personaje personaje = new Personaje(
                        name,
                        description,
                        photo,
                        attack,
                        defense
                );
                elementos.add(personaje);
            }
            return elementos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}