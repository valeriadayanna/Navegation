package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Modelo.Inscritos;
import com.example.myapplication.Modelo.ListaInscritos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Interfaces.InscritosAPI;
import com.example.myapplication.Modelo.ListaInscritos;
import com.google.androidgamesdk.gametextinput.Listener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button btnRetroFit, btnVolley;
    EditText txt;
    RequestQueue requestQueue;
    String baseurl="https://gorest.co.in/public/v1/users";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);
    requestQueue = Volley.newRequestQueue(this);
        btnRetroFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroFit();
            }
        });
    }


    private void RetroFit() {
        Retrofit retro = new Retrofit.Builder().baseUrl("https://gorest.co.in/").addConverterFactory(GsonConverterFactory.create()).build();
        InscritosAPI inscritosAPI = retro.create(InscritosAPI.class);
        Call<ListaInscritos> call = inscritosAPI.list();
        call.enqueue(new Callback<ListaInscritos>() {
            @Override
            public void onResponse(Call<ListaInscritos> call, Response<ListaInscritos> response) {

                try {
                    if (response.isSuccessful()) {
                        ListaInscritos l = response.body();
                        List<Inscritos> list = l.getData();
                        txt.setText("id" + response.code());
                        for (Inscritos ins : list) {
                            String cont = "";
                            cont += ("nombre" + ins.getName() + "/n");
                            cont += ("email" + ins.getEmail() + "/n");
                            cont += ("genero" + ins.getGender() + "/n");
                            cont += ("estado" + ins.getStatus() + "/n");
                            txt.append(cont);
                        }
                    }


                } catch (Exception ex) {
                    txt.setText("Con Retrofit\nNo hay resultados");
                    Toast.makeText(MainActivity.this, "No hay resultados", Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<ListaInscritos> call, Throwable t) {

            }
        });


    }
    private void Volley(String baseurl)
    {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, baseurl, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int tamanio = response.length();
                        for (int i = 0; i < tamanio; i++) {
                            try {
                                JSONObject json = new JSONObject(response.get(i).toString());
                                Inscritos inscritos = new Inscritos(json.getInt("id"),json.get("name"),json.get("email"),json.get("gender"),json.get("status"));
                                txt.append(inscritos.toString());
                            } catch (JSONException ex) {
                                txt.append("No hay resultados");
                                System.out.println(ex.toString());
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ex) {
                txt.append("No hay resultados");
                System.out.println(ex.toString());
            }
        });
        requestQueue.add(jsonRequest);
    }
}



