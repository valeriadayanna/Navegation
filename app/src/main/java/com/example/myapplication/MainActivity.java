package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Modelo.Inscritos;
import com.example.myapplication.Modelo.ListaInscritos;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interfaces.InscritosAPI;



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

    TextView txt1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1=findViewById(R.id.txt);

        RequestQueue requestQueue;


    }


        public void EventClick (View vista)
        {

            RetroFit();

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
                        txt1.setText("id" +response.code());
                        for (Inscritos ins : list) {
                            String cont = "";
                            cont += ("nombre" + ins.getName() + "/n");
                            cont += ("email" + ins.getEmail() + "/n");
                            cont += ("genero" + ins.getGender() + "/n");
                            cont += ("estado" + ins.getStatus() + "/n");
                            txt1.append(cont);
                        }
                    }


                } catch (Exception ex) {
                    txt1.setText("Con Retrofit\nNo hay resultados");

                }


            }

            @Override
            public void onFailure(Call<ListaInscritos> call, Throwable t) {

            }
        });


    }


}



