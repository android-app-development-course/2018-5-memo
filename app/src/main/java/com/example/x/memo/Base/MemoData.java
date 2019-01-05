package com.example.x.memo.Base;

import java.util.Calendar;

public class MemoData {

    private static int ID=0;

    private String mId;
    private String mContext;
    private String mDate;
    private String mPicPath;
    private String mVicPath;
    private String mHide;

    public String getmContext() {
        return mContext;
    }

    public void setmContext(String mContext) {
        this.mContext = mContext;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmPicPath() {
        return mPicPath;
    }

    public void setmPicPath(String mPicPath) {
        this.mPicPath = mPicPath;
        this.VIC=true;
    }

    public String getmVicPath() {
        return mVicPath;
    }

    public void setmVicPath(String mVicPath) {
        this.mVicPath = mVicPath;
        this.PIC=true;
    }

    public String getmHide() {
        return mHide;
    }

    public void setmHide(String mHide) {
        this.mHide = mHide;
    }

    private boolean PIC;
    private boolean VIC;

    public MemoData(){

        if(ID==0)ID=MemoList.getNum();

        this.mContext="";
        this.mId=ID+"";
        ID++;
        this.mDate=new Date().get();

        this.mPicPath="";
        this.mVicPath="";
        this.mHide="false";
        this.PIC=false;
        this.VIC=false;
    }
    public MemoData(String id,String text,String date,String pic,String vic,String hide){
        this.mId=id;
        this.mContext=text;
        this.mDate=date;
        this.mPicPath=pic;
        this.mVicPath=vic;
        this.mHide=hide;

        if(pic.equals("")){
            this.PIC=false;
        }
        else{
            this.PIC=true;
        }
        if(vic.equals("")){
            this.VIC=false;
        }
        else {
            this.VIC=true;
        }
    }

    public String getSortContext(){
        String sContext;
        if(mContext.length()>20)
        {
            sContext=mContext.substring(0,20);
            sContext+="...";
        }
        else {
            sContext=mContext;
        }
        return sContext;
    }
    public String getDateF(){
        String s=mDate.substring(0,10);
        return s;
    }
    public String getDateT(){
        String s=mDate.substring(11,16);
        return s;
    }

    public boolean isPIC(){
        return PIC;
    }

    public boolean isVIC() {
        return VIC;
    }

    class Date {
        int Year;
        int Mon;
        int Day;
        int Hour;
        int Min;
        public Date()
        {
            Calendar calendar=Calendar.getInstance();
            Year=calendar.get(Calendar.YEAR);
            Mon=calendar.get(Calendar.MONTH)+1;
            Day=calendar.get(Calendar.DAY_OF_MONTH);
            Hour=calendar.get(Calendar.HOUR_OF_DAY);
            Min=calendar.get(Calendar.MINUTE);
        }

        public String get() {
            String S=Year+"-";
            if(Mon<10)
            {
                S+="0";
            }
            S=S+Mon+"-";
            if(Day<10)
            {
                S+="0";
            }
            S=S+Day+" ";
            if(Hour<10)
            {
                S+="0";
            }
            S=S+Hour+":";
            if(Min<10)
            {
                S+="0";
            }
            S=S+Min;
            return S;
        }
    }
}

