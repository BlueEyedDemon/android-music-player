package com.sogoodlabs.silvia.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sogoodlabs.silvia.musicplayer.model.PlaylistContract;
import com.sogoodlabs.silvia.musicplayer.model.SongContract;

/**
 * Created by Alexander on 28.08.2017.
 */

public class DBhelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "MusicPlayer.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        for(String query: PlaylistContract.SQL_CREATE_ENTRIES){
            db.execSQL(query);
        }
        //db.execSQL(PlaylistContract.SQL_CREATE_ENTRIES);
        db.execSQL(SongContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String query: PlaylistContract.SQL_DELETE_ENTRIES){
            db.execSQL(query);
        }
        //db.execSQL(PlaylistContract.SQL_DELETE_ENTRIES);
        db.execSQL(SongContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade(db, oldVersion, newVersion);
    }
}
