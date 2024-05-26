package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mirea.kt.ribo.smotrimanime.rec_views.AnimeItem;

import java.util.ArrayList;

public class DBManager {
    private SQLiteOpenHelper sqLiteManager;

    public DBManager(SQLiteOpenHelper sqLiteManager) {
        this.sqLiteManager = sqLiteManager;
    }

    public boolean saveDataAnime(AnimeItem anime){
        SQLiteDatabase db= this.sqLiteManager.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COL_ID,anime.getTitleName());
        cv.put(SQLHelper.COL_NAME,anime.getTitleName());
        cv.put(SQLHelper.COL_DESCR,anime.getDescription());
        cv.put(SQLHelper.COL_GENRES,anime.getGenres());
        cv.put(SQLHelper.COL_IMAGE_URL,anime.getImageUrl());
        cv.put(SQLHelper.COL_RAITING,anime.getRaiting());
        cv.put(SQLHelper.COL_TYPE_ANIME,anime.getTypeAnime());
        cv.put(SQLHelper.COL_FAVORITE,anime.getFavorite());
        cv.put(SQLHelper.COL_FAVORITE,anime.getWatched());
        long result = db.insert(SQLHelper.DB_ANIME_LIST_NAME,null,cv);
        cv.clear();
        db.close();
        return result!=-1;
    }
    public ArrayList<AnimeItem> loadAllAnime(){
        SQLiteDatabase db = this.sqLiteManager.getReadableDatabase();
        ArrayList<AnimeItem> listAnime = new ArrayList<>();
        Cursor dbCursor = db.query(SQLHelper.DB_ANIME_LIST_NAME,null,null,
                null,null,null,null);
        if (dbCursor.moveToFirst()){
            do{
                AnimeItem anime = new AnimeItem();
                anime.setId(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_ID)));
                anime.setTitleName(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_NAME)));
                anime.setDescription(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_DESCR)));
                anime.setGenres(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_GENRES)));
                anime.setImageUrl(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_IMAGE_URL)));
                anime.setTypeAnime(dbCursor.getString(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_TYPE_ANIME)));
                anime.setRaiting(dbCursor.getFloat(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_RAITING)));
                anime.setFavorite(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_FAVORITE)));
                anime.setWatched(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(SQLHelper.COL_WATCHED)));
                listAnime.add(anime);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return listAnime;
    }
}