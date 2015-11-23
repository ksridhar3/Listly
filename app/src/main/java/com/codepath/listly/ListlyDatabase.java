package com.codepath.listly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by k.sridhar on 11/21/2015.
 */
public class ListlyDatabase extends SQLiteOpenHelper {


    private static final String DB_NAME = "listly";
    private static final int DB_VER = 4;
    public static final String TABLE_NAME = "listly_table";
    public static final String COL_ID = "_id";
    public static final String COL_ITEM = "list_item";

    private static final String TAG = ListlyDatabase.class.getSimpleName();

    public ListlyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table listly_table (_id integer primary key autoincrement,list_item text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            db.execSQL("drop table if exists listly_table");
            onCreate(db);
        }
    }
    public long insertItem(String itemStr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("list_item",itemStr);
        long rowID = db.insert("listly_table", null, values);
        return rowID;
    }

    public int deleteItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Long longId = new Long(id);
        return db.delete("listly_table","_id = ?", new String[]{longId.toString()});
    }

    public Cursor getData(int id) {
        Cursor result;
        SQLiteDatabase db = getReadableDatabase();
        result = db.rawQuery("select * from listly_table where _id ="+id+"",null);
        return result;
    }

    public Cursor getAllListItems() {
        ArrayList<String>  array_list = new ArrayList<String>();

        SQLiteDatabase db = getReadableDatabase();
        if(db == null)
            return null;
        Cursor res = db.rawQuery("select * from listly_table", null);
        return res;
    }

    public int updateItem(long id, String itemStr) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("list_item",itemStr);
        Long longID = new Long(id);
        return db.update("listly_table",values,"_id =?", new String[]{longID.toString()});
    }


}
