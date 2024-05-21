package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mirea.kt.ribo.smotrimanime.rec_views.AnimeItem;

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

}
