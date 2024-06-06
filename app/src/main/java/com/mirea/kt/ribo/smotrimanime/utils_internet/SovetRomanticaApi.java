package com.mirea.kt.ribo.smotrimanime.utils_internet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.utils_internet.sovetRomObj.SovetRomanResSearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SovetRomanticaApi {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Retrofit rtRoman;
    private Api api;

    public SovetRomanticaApi(FirebaseAuth mAuth, FirebaseDatabase database) {
        this.mAuth = mAuth;
        this.database = database;
        this.rtRoman =  new Retrofit.Builder()
                .baseUrl("https://service.sovetromantica.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = rtRoman.create(SovetRomanticaApi.Api.class);;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public Retrofit getRtRoman() {
        return rtRoman;
    }

    public void setRtRoman(Retrofit rtRoman) {
        this.rtRoman = rtRoman;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public interface Api {
        @GET("/v1/animesearch")
        Call<ArrayList<SovetRomanResSearch>> getAnimeForName(@Query("anime_name")String name);
    }
}
