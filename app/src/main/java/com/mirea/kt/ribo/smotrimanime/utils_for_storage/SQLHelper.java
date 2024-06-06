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
    public static final String COL_IZBRAN = "izbran";
    public static final String ID_SOVET_ROMAN = "idSovetRom";
    public static final String NAME_ENGLISH = "nameEnglish";
    public static final String SEASON_FOR_YEAR = "seasonYear";
    public static final String YEAR = "year";
    public static final String EPISODES_AIRED = "episodesAired";
    public static final String PG = "PG";
    public static final String STATUS = "status";


//    private String titleName,titleNameEnglish,status,seasonYear,genres,description,imageUrl,typeAnime,PG;
//    private float raiting;
//    private int favorite,watched,id,izbran,year,episodesAired;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ANIME_LIST_USER (" +
                "id integer primary key," +
                "idSovetRom integer," +
                "name text," +
                "nameEnglish text," +
                "description text," +
                "raiting float," +
                "genres text," +
                "imageUrl text," +
                "typeAnime text," +
                "seasonYear text," +
                "year integer," +
                "episodesAired integer," +
                "PG text," +
                "status text," +
                "favorite integer," +
                "watched integer," +
                "izbran integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+SQLHelper.DB_ANIME_LIST_NAME);
        onCreate(db);
    }
}
