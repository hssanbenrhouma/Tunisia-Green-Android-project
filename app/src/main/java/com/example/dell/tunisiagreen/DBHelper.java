package com.example.dell.tunisiagreen;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dell on 27/04/2016.
 */
 public class DBHelper extends SQLiteOpenHelper{


    public DBHelper(Context context, String dbname, SQLiteDatabase.CursorFactory cursorFactory,int version) {

        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User (id	INTEGER NOT NULL,username	TEXT NOT NULL,email	TEXT NOT NULL,type	TEXT NOT NULL,region	TEXT NOT NULL,password	TEXT NOT NULL, PRIMARY KEY(id))");
        System.out.println("creation table !!");
        db.execSQL("CREATE TABLE IF NOT EXISTS Declaration (id	INTEGER NOT NULL,idcitoyen	INTEGER NOT NULL,idresponsable	INTEGER NOT NULL,region TEXT NOT NULL,description TEXT ,date	TEXT NOT NULL,urlimage	TEXT NOT NULL,longitude	Double NOT NULL,latitude Double NOT NULL, PRIMARY KEY(id))");
        System.out.println("creation table 2!!");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Declaration");
        onCreate(db);
    }
}