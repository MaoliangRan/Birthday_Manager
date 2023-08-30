package com.example.birthday_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//user_info存用户信息和设置，person存生日信息
public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person_info.db";
    private static final int version = 1;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists user_info (name TEXT,birthday TEXT,face BLOB,voice INTEGER,vibrate INTEGER,bar INTEGER,status INTEGER,primary key(name))");
        db.execSQL("create table if not exists person (remind_time INTEGER,name TEXT,birthday TEXT,year INTEGER,month INTEGER,day INTEGER,sex INTEGER,remarkinfo TEXT,face BLOB)");

        db.execSQL("INSERT INTO user_info(name,birthday,face,voice,vibrate,bar,status)values('生日管家','2021年1月3日',null,1,1,1,1)");
        //db.execSQL("INSERT INTO person(remind_time,name,birthday,year,month,day,sex,remarkinfo,face)values(2,'劳动节',null,1900,5,1,null,null,null)");
        //db.execSQL("INSERT INTO person(remind_time,name,birthday,year,month,day,sex,remarkinfo,face)values(2,'国庆节',null,1900,10,1,null,null,null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
