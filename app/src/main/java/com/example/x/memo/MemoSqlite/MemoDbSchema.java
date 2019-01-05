package com.example.x.memo.MemoSqlite;

public class MemoDbSchema {
    public static final class MemoTable{
        public static final String NAME="MEMO";
        public static final class Cols{
            public static final String ID="id";
            public static final String CONTEXT="context";
            public static final String DATE="date";
            public static final String PICPATH="picturepath";
            public static final String VICPATH="voicepath";
            public static final String HIDE="hide";
        }
    }
}
