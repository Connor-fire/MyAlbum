package com.example.myalbum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static  final  String albums ="create table albums ("
            +"AlbumName String primary key ,"
            +"Path String )";

    private  Context mContext;

    public MyDataBaseHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(albums);
        Toast.makeText(mContext,"Create success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}


