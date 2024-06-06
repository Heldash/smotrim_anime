package com.mirea.kt.ribo.smotrimanime.utils_internet;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mirea.kt.ribo.smotrimanime.utils_for_storage.AnimePageInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckVideoInSite extends Worker {
    private ArrayList<Integer> episodesList;
    private int version,sovetId;
    private String folderSite;
    public CheckVideoInSite(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
//        episodesList = new ArrayList<>();
        this.sovetId = getInputData().getInt("sovetId",0);
        this.folderSite = getInputData().getString("folder");
    }
    @NonNull
    @Override
    public Result doWork() {
        Log.d("CheckVideoInSite","workerStart");
        OkHttpClient client = new OkHttpClient();
        for (int v=1;v<7;v++){
            String urlAnime = AnimePageInfo.convertToUrlSovetRom(1,v,
                    sovetId,folderSite);
            Request request = new Request.Builder()
                    .url(urlAnime).build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()){
                    Log.d("StatusWorkRequest",""+response.code()+
                            "\n"+urlAnime);
                }else{
                    Log.d("StatusWorkRequest","Succes"+response.code()+
                            "\n"+urlAnime);
                    version = v;
                    Data output = new Data.Builder()
                            .putInt("version", version)
                            .build();
                    setProgressAsync(new Data.Builder().putInt("progress",1).build());
                    return Result.success(output);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.failure();
    }

    public ArrayList<Integer> getEpisodesList() {
        return episodesList;
    }

    public void setEpisodesList(ArrayList<Integer> episodesList) {
        this.episodesList = episodesList;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSovetId() {
        return sovetId;
    }

    public void setSovetId(int sovetId) {
        this.sovetId = sovetId;
    }

    public String getFolderSite() {
        return folderSite;
    }

    public void setFolderSite(String folderSite) {
        this.folderSite = folderSite;
    }
}
