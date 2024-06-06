package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.rec_views.AnimeItem;

import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {
    private SQLHelper sqLiteManager;

    public DBManager(SQLHelper sqLiteManager) {
        this.sqLiteManager = sqLiteManager;
    }
    private FirebaseDatabase database;

    public DBManager(SQLHelper sqLiteManager, FirebaseDatabase database) {
        this.sqLiteManager = sqLiteManager;
        this.database = database;
    }

    public boolean addDataAnime(AnimeItem anime){
        SQLiteDatabase db= this.sqLiteManager.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("inserting_anime",anime.toString());
        cv.put(SQLHelper.COL_ID,anime.getId());
        cv.put(SQLHelper.COL_NAME,anime.getTitleName());
        cv.put(SQLHelper.COL_DESCR,anime.getDescription());
        cv.put(SQLHelper.COL_GENRES,anime.getGenres());
        cv.put(SQLHelper.COL_IMAGE_URL,anime.getImageUrl());
        cv.put(SQLHelper.COL_RAITING,anime.getRaiting());
        cv.put(SQLHelper.COL_TYPE_ANIME,anime.getTypeAnime());
        cv.put(SQLHelper.COL_FAVORITE,anime.getFavorite());
        cv.put(SQLHelper.COL_WATCHED,anime.getWatched());
        cv.put(SQLHelper.COL_IZBRAN,anime.getIzbran());
        cv.put(SQLHelper.ID_SOVET_ROMAN,anime.getIdSovetRoman());
        cv.put(SQLHelper.NAME_ENGLISH,anime.getTitleNameEnglish());
        cv.put(SQLHelper.SEASON_FOR_YEAR,anime.getSeasonYear());
        cv.put(SQLHelper.YEAR,anime.getYear());
        cv.put(SQLHelper.EPISODES_AIRED,anime.getEpisodesAired());
        cv.put(SQLHelper.PG,anime.getPG());
        cv.put(SQLHelper.STATUS,anime.getStatus());
        long result = db.insert(SQLHelper.DB_ANIME_LIST_NAME,null,cv);

        cv.clear();
        db.close();
        return result!=-1;
    }
    public AnimeItem collectAnimeItem(Cursor dbCursor){
        AnimeItem anime = new AnimeItem();
        anime.setId(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_ID)));
        anime.setTitleName(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_NAME)));
        anime.setDescription(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_DESCR)));
        anime.setGenres(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_GENRES)));
        anime.setImageUrl(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_IMAGE_URL)));
        anime.setTypeAnime(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_TYPE_ANIME)));
        anime.setRaiting(dbCursor.getFloat(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_RAITING)));

        anime.setIdSovetRoman(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.ID_SOVET_ROMAN)));

        anime.setTitleNameEnglish(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.NAME_ENGLISH)));
        anime.setSeasonYear(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.SEASON_FOR_YEAR)));
        anime.setYear(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.YEAR)));
        anime.setEpisodesAired(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.EPISODES_AIRED)));
        anime.setPG(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.PG)));
        anime.setStatus(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.STATUS)));


        anime.setFavorite(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_FAVORITE)));
        anime.setWatched(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_WATCHED)));
        anime.setIzbran(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_IZBRAN)));
        return anime;
    }
    public ArrayList<AnimeItem> loadAllAnime(){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        ArrayList<AnimeItem> listAnime = new ArrayList<>();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,null,
                null,null,null,null);
        if (dbCursor.moveToFirst()){
            do{
                AnimeItem anime = collectAnimeItem(dbCursor);
                listAnime.add(anime);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return listAnime;
    }
    public ArrayList<AnimeItem> loadAllFavorite(){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        ArrayList<AnimeItem> listAnime = new ArrayList<>();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,
                SQLHelper.COL_FAVORITE+"= 1",null,null,null,null);
        if (dbCursor.moveToFirst()){
            do{
                AnimeItem anime = collectAnimeItem(dbCursor);
                listAnime.add(anime);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return listAnime;
    }
    public ArrayList<AnimeItem> loadAllWatched(){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        ArrayList<AnimeItem> listAnime = new ArrayList<>();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,
                SQLHelper.COL_WATCHED+"= 1",null,null,null,null);
        if (dbCursor.moveToFirst()){
            do{
                AnimeItem anime = collectAnimeItem(dbCursor);
                listAnime.add(anime);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return listAnime;
    }
    public ArrayList<AnimeItem> loadAllIzbran(){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        ArrayList<AnimeItem> listAnime = new ArrayList<>();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,
                SQLHelper.COL_IZBRAN+"= 1",null,null,null,null);
        if (dbCursor.moveToFirst()){
            do{
                AnimeItem anime = collectAnimeItem(dbCursor);
                listAnime.add(anime);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return listAnime;
    }
    public boolean checkAnimeDatabase(AnimeItem anime){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,"id = ?",
                new String[]{String.valueOf(anime.getId())},null,null,null);
        boolean result = dbCursor.moveToFirst();
        dbCursor.close();
        db.close();
        return result;
    }
    public AnimeItem getAnime(AnimeItem anime){
        int id = anime.getId();
        return getAnime(id);
    }
    public AnimeItem getAnime(int id){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,"id = ?",
                new String[]{String.valueOf(id)},null,null,null);
        AnimeItem animeRes = new AnimeItem();
        if (dbCursor.moveToFirst()){
            animeRes = collectAnimeItem(dbCursor);
        }
        dbCursor.close();
        db.close();
        return animeRes;
    }
    public boolean hasAnimeInDatabase(int id){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,"id = ?",
                new String[]{String.valueOf(id)},null,null,null);
        boolean res = dbCursor.moveToFirst();
        dbCursor.close();
        db.close();
        return res;
    }
    public boolean addFavoriteAnime(int id){

            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(sqLiteManager.COL_FAVORITE,1);
            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                    new String[]{""+id});
            cv.clear();
            db.close();
            return res !=-1;
//            addDataAnime(anime);
//            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put(sqLiteManager.COL_FAVORITE,1);
//            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
//                    new String[]{""+anime.getId()});
//            cv.clear();
//            db.close();
//            return res !=-1;
    }
    public boolean diasableFavoriteAnime(int id){
        if (hasAnimeInDatabase(id)) {
            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(sqLiteManager.COL_FAVORITE,0);
            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                    new String[]{""+id});
            cv.clear();
            db.close();
            return res !=-1;
        }else{
        }
        return false;
    }
    public boolean addIzbranAnime(int id){
        if (hasAnimeInDatabase(id)) {
            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(sqLiteManager.COL_IZBRAN,1);
            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                    new String[]{""+id});
            cv.clear();
            db.close();
            return res !=-1;
        }else{
        }
        return false;
    }
    public boolean diasableIzbranAnime(int id){
        if (hasAnimeInDatabase(id)){
            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(sqLiteManager.COL_IZBRAN,0);
            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                    new String[]{""+id});
            cv.clear();
            db.close();
            return res !=-1;
        }else{
        }
        return false;
    }
    public boolean addWatchedAnime(int id){
        SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(sqLiteManager.COL_WATCHED,1);
        Log.d("2143567543",cv.toString());
        long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                new String[]{""+id});
        cv.clear();
        db.close();
        return res !=-1;
    }
    public boolean diasableWatchedAnime(int id){
        if (hasAnimeInDatabase(id)){
            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(sqLiteManager.COL_WATCHED,0);
            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
                    new String[]{""+id});
            cv.clear();
            db.close();
            return res !=-1;
        }else{
//            addDataAnime(anime);
//            SQLiteDatabase db = this.sqLiteManager.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put(sqLiteManager.COL_WATCHED,1);
//            long res = db.update(sqLiteManager.DB_ANIME_LIST_NAME, cv,"id = ?",
//                    new String[]{""+anime.getId()});
//            cv.clear();
//            db.close();
//            return res !=-1;
        }
        return false;
    }
    public StateAnime statusUserAnime(AnimeItem anime){
        int id = anime.getId();
        return statusUserAnime(id);
    }
    public class StateAnime{
        boolean favorite,izbran,watched;

        public StateAnime(HashMap<String,Boolean> status) {
            this.favorite = status.get("favorited");
            this.izbran = status.get("izbran");
            this.watched = status.get("watched");
        }

        public boolean isFavorite() {
            return favorite;
        }

        public boolean isIzbran() {
            return izbran;
        }

        public boolean isWatched() {
            return watched;
        }
    }
    public StateAnime statusUserAnime(int id){
        AnimeItem anime = getAnime(id);
        HashMap<String,Boolean> result = new HashMap<>();
        result.put("favorited",anime.getFavorite()==1);
        result.put("izbran",anime.getIzbran()==1);
        result.put("watched",anime.getWatched()==1);
        return new StateAnime(result);
    }
}