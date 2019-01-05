package com.example.x.memo.MemoSqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.x.memo.Base.MemoData;

public class MemoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MemoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MemoData getMemo(){

        String id= getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.ID));
        String context=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.CONTEXT));
        String date=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.DATE));
        String picpath=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.PICPATH));
        String vicpath=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.VICPATH));
        String hide=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.HIDE));

        MemoData memoData=new MemoData(id,context,date,picpath,vicpath,hide);
        return memoData;
    }
}
