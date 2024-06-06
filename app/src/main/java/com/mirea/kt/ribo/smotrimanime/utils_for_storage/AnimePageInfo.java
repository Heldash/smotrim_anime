package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.mirea.kt.ribo.smotrimanime.AnimePageActivity;
import com.mirea.kt.ribo.smotrimanime.utils_internet.CheckVideoInSite;

import okhttp3.OkHttpClient;

public class AnimePageInfo {

    private String description;
    private boolean hasVideo;
    private String imageUrl;
    private String scaningVideo = "scan";
    private OkHttpClient client;
    private String PG;
    Context context;
    private int series;
    private String genres;
    private int sovetId;
    private String folderInSite;
    private int idShiki;
    private int version;
    private String nameAnime;

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public AnimePageInfo(Bundle bundle,Context context,AnimePageActivity animePage) {
        this.context = context;
        description = bundle.getString("descr");
        if (description==null||description.isEmpty()){
            description = "Отсутствует";
        }
        imageUrl = "https://shikimori.one"+bundle.getString("image_url");
        PG = bundle.getString("PG").toUpperCase().replace("_"," ");
        series = bundle.getInt("series");
        client = new OkHttpClient();
        hasVideo = bundle.getBoolean("has_video");
        if (hasVideo){
            sovetId = bundle.getInt("sovet_id");
            folderInSite = bundle.getString("folderInSite");

            Data myData = new Data.Builder()
                    .putString("folder", folderInSite)
                    .putInt("sovetId", getSovetId())
                    .build();
            WorkManager manager = WorkManager.getInstance(context);
            OneTimeWorkRequest workRequest =
                    new OneTimeWorkRequest.Builder(CheckVideoInSite.class)
                            .setInputData(myData).build();
            manager.enqueue(workRequest);
            manager.getWorkInfoByIdLiveData(workRequest.getId()).observe(animePage,
                    new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            Log.d("CheckVideoInSite","State: "+workInfo.getState());
                            if (workInfo.getState().name().equals("SUCCEEDED")){
                                version = workInfo.getOutputData().getInt("version",0);
                                scaningVideo = "ready";
                                hasVideo = true;
                            }else if (workInfo.getState().name().equals("FAILED")){
                                version = 0;
                                scaningVideo = "ready";
                                hasVideo = false;
                            }
                        }
                    });
        }else{
            sovetId = -1;
            folderInSite = null;
        }
        genres = String.join(", ",bundle.getStringArrayList("genres"));
        idShiki = bundle.getInt("idShiki");
        nameAnime = bundle.getString("nameAnime");
    }
//"https://scu1.sovetromantica.com/anime/1488_pon-no-michi/episodes/subtitles/episode_1/episode_1_1080p.m3u8"
    public static String convertToUrlSovetRom(int episode,int version,int sovetId,
                                              String folderInSite){
        String resUrl ="https://scu"+version+".sovetromantica.com/anime/";
        resUrl+= sovetId+"_"+folderInSite+"/";
        resUrl +="episodes/subtitles/episode_"+episode+"/episode_"+1+/*"_"+resolution+"p*/".m3u8";
        Log.d("frerer",resUrl);
        return resUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPG() {
        return PG;
    }

    public int getSeries() {
        return series;
    }

    public String getGenres() {
        return genres;
    }

    public int getSovetId() {
        return sovetId;
    }

    public String getFolderInSite() {
        return folderInSite;
    }

    public int getIdShiki() {
        return idShiki;
    }

    public String getNameAnime() {
        return nameAnime;
    }

    public String getScaningVideo() {
        return scaningVideo;
    }

    public void setScaningVideo(String scaningVideo) {
        this.scaningVideo = scaningVideo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
