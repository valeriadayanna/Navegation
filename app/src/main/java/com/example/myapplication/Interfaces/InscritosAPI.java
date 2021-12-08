package com.example.myapplication.Interfaces;


import retrofit2.Call;
import retrofit2.http.GET;
import com.example.myapplication.Modelo.Inscritos;
import com.example.myapplication.Modelo.ListaInscritos;

import java.util.List;

public interface InscritosAPI {
    @GET("public/v1/users")
   public Call<ListaInscritos> list();
}
