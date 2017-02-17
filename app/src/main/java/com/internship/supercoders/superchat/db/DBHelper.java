package com.internship.supercoders.superchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.internship.supercoders.superchat.data.AppConsts.LOG_TAG;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SuperChat", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        createAuthTable(db);
        createMyInfoTable(db);
        createMyContactsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createAuthTable(SQLiteDatabase db) {
        DBInfo dbInfo = new DBInfo();
        db.execSQL("create table " + dbInfo.authTableName + " ("
                + "id integer primary key autoincrement,"
                + dbInfo.emailRow + " text,"
                + dbInfo.passRow + " text,"
                + dbInfo.tokenRow + " text,"
                + dbInfo.timeRow + " long"
                + ");");
    }

    private void createMyInfoTable(SQLiteDatabase db) {
        db.execSQL("create table myInfo ("
                + "id integer primary key autoincrement, my_id integer, login text, password text, email text, " +
                "full_name text, phone text, website text, blob_id text, facebook_id text, photo_path text );");
    }

    private void createMyContactsTable(SQLiteDatabase db) {
        db.execSQL("create table myContacts ("
                + "id integer primary key autoincrement, my_id integer, login text, password text, email text, " +
                "full_name text, phone text, website text, blob_id text, facebook_id text, photo_path text );");

    }


}
