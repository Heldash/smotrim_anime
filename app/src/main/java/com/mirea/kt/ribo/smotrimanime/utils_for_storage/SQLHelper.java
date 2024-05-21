package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DB_ANIME_LIST_NAME = "ANIME_LIST_USER";
    public SQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCR = "description";
    public static final String COL_RAITING = "raiting";
    public static final String COL_GENRES = "genres";
    public static final String COL_IMAGE_URL = "imageUrl";
    public static final String COL_TYPE_ANIME = "typeAnime";
    public static final String COL_FAVORITE = "favorite";
    public static final String COL_WATCHED = "watched";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ANIME_LIST_USER (" +
                "id integer," +
                "name text," +
                "description text," +
                "raiting float," +
                "genres text," +
                "imageUrl text," +
                "typeAnime text," +
                "favorite integer," +
                "watched integer);");
//        favorite = 0|1
//        watched = 0|1
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
