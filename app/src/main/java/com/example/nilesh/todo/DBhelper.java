package com.example.nilesh.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Nilesh on 16-01-2017.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static  final String DB_NAME="DATABASE";
    private static final int DB_VER=1;
    private static final String DB_TABLE="Table";
    private static final String DB_COLUMN="TASKNAME";



    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        String query=String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
        db.execSQL(query);}catch(RuntimeException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertnewtask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_COLUMN,task);
        db.insertWithOnConflict(DB_TABLE,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deletetask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+ " - ? ",new String[]{task});
        db.close();
    }


    public ArrayList<String> gettasklist(){
        ArrayList<String> tasklist=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index=cursor.getColumnIndex(DB_COLUMN);
            tasklist.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return tasklist;
    }
}
