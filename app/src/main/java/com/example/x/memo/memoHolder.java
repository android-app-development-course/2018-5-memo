package com.example.x.memo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x.memo.Base.MemoData;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class memoHolder extends SwipeMenuRecyclerView.ViewHolder{

    private MemoData mData;

    private TextView mContext;
    private TextView mDate;
    private ImageView pic;
    private ImageView vic;


    public memoHolder(View itemView) {
        super(itemView);

        mContext=(TextView)itemView.findViewById(R.id.item_text);
        mDate=(TextView)itemView.findViewById(R.id.item_date);
        pic=(ImageView)itemView.findViewById(R.id.item_photo);
        vic=(ImageView)itemView.findViewById(R.id.item_voice);
        pic.setVisibility(View.VISIBLE);
        vic.setVisibility(View.VISIBLE);
    }

    public void bind(final MemoData memoData){

        mData=memoData;
        mContext.setText(mData.getSortContext());
        mDate.setText(mData.getmDate());
        if(!memoData.isPIC())
        {
            pic.setVisibility(View.INVISIBLE);
        }
        else
        {
            pic.setVisibility(View.VISIBLE);
        }
        if(!memoData.isVIC())
        {
            vic.setVisibility(View.INVISIBLE);
        }
        else {
            vic.setVisibility(View.VISIBLE);
        }
    }
}
