package com.example.x.memo.MemoSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.x.memo.MemoSqlite.MemoDbSchema.MemoTable;

public class MemoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATEBASE_NAME="MemoBase.db";

    public MemoBaseHelper(Context context) {
        super(context,DATEBASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+MemoTable.NAME+ "("+"_id integer primary key autoincrement, "+
                MemoTable.Cols.ID+", "+
                MemoTable.Cols.CONTEXT+", "+
                MemoTable.Cols.DATE+", "+
                MemoTable.Cols.PICPATH+", "+
                MemoTable.Cols.VICPATH+", "+
                MemoTable.Cols.HIDE+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
