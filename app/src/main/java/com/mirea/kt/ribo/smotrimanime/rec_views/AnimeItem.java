package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.util.Log;

import com.mirea.kt.ribo.smotrimanime.utils_for_storage.Account;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.SQLHelper;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimesObj;

import org.checkerframework.checker.units.qual.A;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AnimeItem {
    private String titleName,titleNameEnglish,status,seasonYear,genres,description,imageUrl,typeAnime,PG;
    private float raiting;
    private int favorite,watched,id,izbran,year,episodesAired,idSovetRoman,episodes;
    private AnimesObj animeShiki;

    public AnimeItem() {

    }

    public AnimeItem(AnimesObj animeShiki) {
        this.animeShiki = animeShiki;
        Account dfdefddf= new Account();
        this.titleName = animeShiki.getRussian();
        this.titleNameEnglish = animeShiki.getName();
        if (animeShiki.getStatus()!=null) {
            Log.d("status",""+animeShiki.getStatus());
            switch (animeShiki.getStatus()) {
                case "anons":
                    this.status = "анонс";
                    break;
                case "ongoing":
                    this.status = "онгоинг";
                    break;
                case "released":
                    this.status = "завершено";
                    break;
                default:
                    this.status = "Неизвестно";
                    break;
            }
        }
        if (animeShiki.getAiredOn()==null||animeShiki.getAiredOn().equals("null")){
            this.seasonYear = "неизвестно";
            this.year = -1;
        }else{
            DateFormat df = new SimpleDateFormat("y-MM-d");
            Date dateAnime;

            try {
                dateAnime = df.parse(animeShiki.getAiredOn());
                Log.d("Date_anime",""+dateAnime.getYear()+"\n"+
                        dateAnime.getMonth()+"Date "+animeShiki.getAiredOn());
                this.year = 1900+dateAnime.getYear();

                if (dateAnime.getMonth()>=1 &&dateAnime.getMonth()<=2 || dateAnime.getMonth()==12){
                    this.seasonYear = "зима";
                } else if (dateAnime.getMonth()>=3 &&dateAnime.getMonth()<=5) {
                    this.seasonYear = "весна";
                } else if (dateAnime.getMonth()>=6 &&dateAnime.getMonth()<=8) {
                    this.seasonYear = "лето";
                } else{
                    this.seasonYear = "осень";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.imageUrl = "https://shikimori.one"+animeShiki.getImage().getOriginal();
        this.typeAnime = animeShiki.getKind();
        this.raiting = animeShiki.getScore();
        this.id = animeShiki.getId();
        if (animeShiki.getStatus()!= null && animeShiki.getStatus()=="released"){
            this.episodesAired = animeShiki.getEpisodes();
        }else {
            this.episodesAired = animeShiki.getEpisodesAired();
        }
        this.episodes = animeShiki.getEpisodes();
        this.favorite =0;
        this.watched = 0;
        this.izbran = 0;
    }

//    public AnimeItem(String titleName, String titleNameEnglish, String status, String seasonYear, String genres, String description, String imageUrl, String typeAnime, String PG, float raiting, int favorite, int watched, int id, int izbran, int year, int episodesAired) {
//        this.titleName = titleName;
//        this.titleNameEnglish = titleNameEnglish;
//        this.status = status;
//        this.seasonYear = seasonYear;
//        this.genres = genres;
//        this.description = description;
//        this.imageUrl = imageUrl;
//        this.typeAnime = typeAnime;
//        this.PG = PG;
//        this.raiting = raiting;
//        this.favorite = favorite;
//        this.watched = watched;
//        this.id = id;
//        this.izbran = izbran;
//        this.year = year;
//        this.episodesAired = episodesAired;
//    }

    public AnimeItem(String titleName, String imageUrl, String typeAnime, float raiting, int id) {
        this.titleName = titleName;
        this.imageUrl = imageUrl;
        this.typeAnime = typeAnime;
        this.raiting = raiting;
        this.id = id;
        this.favorite =0;
        this.watched = 0;
        this.izbran = 0;
    }
//    String titleName, String imageUrl, String typeAnime, float raiting, int id
    public static ArrayList<AnimeItem> convertAnimeObj(ArrayList<AnimesObj> animeResponse,
                                                       DBManager dbManager){
        ArrayList<AnimeItem> animes = new ArrayList<>();
        if (!animeResponse.isEmpty()){
            for (AnimesObj anim :animeResponse){
                if(!dbManager.hasAnimeInDatabase(anim.getId())) {
                    Log.d("No has in database anime",""+anim.getName());
                    boolean res = dbManager.addDataAnime(new AnimeItem(anim));
                    if (res){
                        Log.e("Error added database",""+anim.getName());
                    }
                }
                animes.add(new AnimeItem(anim));
//                animes.add(new AnimeItem(name,"https://shikimori.one"+anim.getImage().getOriginal(),
//                        anim.getKind(), anim.getScore(), anim.getId()));
            }
        }
        return animes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIzbran() {
        return izbran;
    }

    public void setIzbran(int izbran) {
        this.izbran = izbran;
    }

    public AnimeItem(String titleName, String genres, String description, String imageUrl, String typeAnime, float raiting, int favorite, int watched, int id, int izbran) {
        this.titleName = titleName;
        this.genres = genres;
        this.description = description;
        this.imageUrl = imageUrl;
        this.typeAnime = typeAnime;
        this.raiting = raiting;
        this.favorite = favorite;
        this.watched = watched;
        this.id = id;
        this.izbran = izbran;
    }

    //    public AnimeItem(String titleName, String genres, String description, String imageUrl, String typeAnime, float raiting, int favorite, int watched, int id) {
//        this.titleName = titleName;
//        this.genres = genres;
//        this.description = description;
//        this.imageUrl = imageUrl;
//        this.typeAnime = typeAnime;
//        this.raiting = raiting;
//        this.favorite = favorite;
//        this.watched = watched;
//        this.id = id;
//    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTypeAnime() {
        return typeAnime;
    }

    public void setTypeAnime(String typeAnime) {
        this.typeAnime = typeAnime;
    }

    public float getRaiting() {
        return raiting;
    }

    public void setRaiting(float raiting) {
        this.raiting = raiting;
    }

    public String getTitleNameEnglish() {
        return titleNameEnglish;
    }

    public void setTitleNameEnglish(String titleNameEnglish) {
        this.titleNameEnglish = titleNameEnglish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getPG() {
        return PG;
    }

    public void setPG(String PG) {
        this.PG = PG;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    public void setEpisodesAired(int episodesAired) {
        this.episodesAired = episodesAired;
    }

    public int getIdSovetRoman() {
        return idSovetRoman;
    }

    public void setIdSovetRoman(int idSovetRoman) {
        this.idSovetRoman = idSovetRoman;
    }

    public AnimesObj getAnimeShiki() {
        return animeShiki;
    }

    public void setAnimeShiki(AnimesObj animeShiki) {
        this.animeShiki = animeShiki;
    }

    @Override
    public String toString() {
        return "AnimeItem{" +
                "titleName='" + titleName + '\'' +
                ", titleNameEnglish='" + titleNameEnglish + '\'' +
                ", status='" + status + '\'' +
                ", seasonYear='" + seasonYear + '\'' +
                ", genres='" + genres + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", typeAnime='" + typeAnime + '\'' +
                ", PG='" + PG + '\'' +
                ", raiting=" + raiting +
                ", favorite=" + favorite +
                ", watched=" + watched +
                ", id=" + id +
                ", izbran=" + izbran +
                ", year=" + year +
                ", episodesAired=" + episodesAired +
                ", idSovetRoman=" + idSovetRoman +
                ", episodes=" + episodes +
                ", animeShiki=" + animeShiki +
                '}';
    }
}
