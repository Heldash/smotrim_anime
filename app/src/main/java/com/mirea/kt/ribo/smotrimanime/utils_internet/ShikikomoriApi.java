package com.mirea.kt.ribo.smotrimanime.utils_internet;

import android.util.Log;
import android.view.PixelCopy;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.Account;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimePodrob;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimesObj;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.RefreshTokenObj;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ShikikomoriApi {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Retrofit rtShiki;
    private Api api;
    private TokenShiki tokenShiki;


    public ShikikomoriApi(FirebaseAuth mAuth, FirebaseDatabase database) {
        this.mAuth = mAuth;
        this.database = database;
        rtShiki = new Retrofit.Builder()
                .baseUrl("https://shikimori.one")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = rtShiki.create(Api.class);

    }

    public void refreshingTokens(String clientId,String clientSecret,
                                  String refresh_token,String UserAgent){
        Call<RefreshTokenObj> call = api.getRefreshToken("refresh_token",
                clientId,clientSecret,refresh_token,UserAgent);
        call.enqueue(new Callback<RefreshTokenObj>() {
            @Override
            public void onResponse(Call<RefreshTokenObj> call, Response<RefreshTokenObj> response) {
                if (response.isSuccessful()) {
                    Log.d("sfsaasdsadsadsadsadsa","esdadsad");
                    RefreshTokenObj refToken = response.body();
                    DatabaseReference databaseReference = database.getReference("tokens");
                    databaseReference.child("Acces_token").setValue(refToken.getAccessToken());
                    databaseReference.child("refresh_token").setValue(refToken.getRefreshToken());
                } else{
                    Log.d("Refresh_token_get",""+response.errorBody()+"\n"+response.body());
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenObj> call, Throwable t) {
                Log.e("Exeption callback refresh",t.getMessage());
            }
        });
//        (@Field("grant_type") String grType,
//                @Field("client_id")String clientId,
//                @Field("client_secret") String clientSecret,
//                @Field("refresh_token")String refToken,
//                @Header("User-Agent")String userAgent);

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

    public Retrofit getRtShiki() {
        return rtShiki;
    }

    public void setRtShiki(Retrofit rtShiki) {
        this.rtShiki = rtShiki;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }


    public interface Api {
        @GET("/api/animes")
        Call<ArrayList<AnimesObj>> getAnimesByName(@Query("search")String name,@Query("limit")int lim,
                                        @Header("User-Agent") String userAgent,
                                        @Header("Authorization") String accesToken);

        @GET("/api/animes")
        Call<ArrayList<AnimesObj>> getAllAnime(@Query("page")int page, @Query("limit")int limit,
                                    @Query("oreder")String orderBy,
                                    @Header("User-Agent") String userAgent,
                                    @Header("Authorization") String accesToken);
        @FormUrlEncoded
        @POST("/oauth/token")
        Call<RefreshTokenObj> getRefreshToken(@Field("grant_type") String grType,
                                              @Field("client_id")String clientId,
                                              @Field("client_secret") String clientSecret,
                                              @Field("refresh_token")String refToken,
                                              @Header("User-Agent")String userAgent);
        @GET("/api/animes/{id}")
        Call<AnimePodrob> getAnimeForId(@Path("id")int id,
                                        @Header("User-Agent")String userAgent,
                                        @Header("Authorization") String accesToken);

    }
    
}
