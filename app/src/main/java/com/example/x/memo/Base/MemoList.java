package com.example.x.memo.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.memo.MemoSqlite.MemoBaseHelper;
import com.example.x.memo.MemoSqlite.MemoCursorWrapper;
import com.example.x.memo.MemoSqlite.MemoDbSchema;

import java.util.ArrayList;
import java.util.List;

public class MemoList {

    private static MemoList sMemoList;
    private static int num;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MemoList get(Context context){
        if(sMemoList==null){
            sMemoList=new MemoList(context);
        }
        return sMemoList;
    }

    private MemoList(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new MemoBaseHelper(mContext).getWritableDatabase();
    }

    public List<MemoData> getMemos(){

        List<MemoData> memoDatas=new ArrayList<>();
        MemoCursorWrapper cursor=queryMemo(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                memoDatas.add(cursor.getMemo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        num=memoDatas.size();
        return memoDatas;
    }
    public List<MemoData> getMemos(String hide){

        List<MemoData> memoDatas=new ArrayList<>();
        MemoCursorWrapper cursor=queryMemo(MemoDbSchema.MemoTable.Cols.HIDE+"=?",
                new String[]{hide});
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                memoDatas.add(cursor.getMemo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return memoDatas;
    }

    public MemoData getMemo(String id){

        MemoCursorWrapper cursor=queryMemo(
                MemoDbSchema.MemoTable.Cols.ID+"=?",
                new String[]{id}
        );
        try {
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMemo();
        }finally {
            cursor.close();
        }
    }

    public void updateMemo(MemoData memoData){
        String id=memoData.getmId();
        ContentValues values=getContentValues(memoData);
        mDatabase.update(MemoDbSchema.MemoTable.NAME,values, MemoDbSchema.MemoTable.Cols.ID+"=?",new String[]{id});
    }

    public void deleteMemo(String id){
        mDatabase.delete(MemoDbSchema.MemoTable.NAME,MemoDbSchema.MemoTable.Cols.ID+"=?",new String[]{id});
    }

    private static ContentValues getContentValues(MemoData memoData){
        ContentValues values=new ContentValues();
        values.put(MemoDbSchema.MemoTable.Cols.ID ,memoData.getmId());
        values.put(MemoDbSchema.MemoTable.Cols.CONTEXT,memoData.getmContext());
        values.put(MemoDbSchema.MemoTable.Cols.DATE ,memoData.getmDate());
        values.put(MemoDbSchema.MemoTable.Cols.HIDE,memoData.getmHide());
        values.put(MemoDbSchema.MemoTable.Cols.PICPATH ,memoData.getmPicPath());
        values.put(MemoDbSchema.MemoTable.Cols.VICPATH,memoData.getmVicPath());
        return values;
    }

    private MemoCursorWrapper queryMemo(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                MemoDbSchema.MemoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MemoCursorWrapper(cursor);
    }

    public void addMemo(MemoData memoData){

        ContentValues values=getContentValues(memoData);
        mDatabase.insert(MemoDbSchema.MemoTable.NAME,null,values);
    }

    public static int getNum() {
        return num;
    }
}
