package com.example.x.memo;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.memo.Base.MemoData;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

public class MemoAdapter extends SwipeMenuRecyclerView.Adapter<memoHolder> {

    private List<MemoData> memoDataList;

    private onclickListener listener;//将接口作为成员变量
    public void setListener(onclickListener listener) { this.listener = listener; }
    public interface onclickListener{ void onItemClick(View view,int position); }//添加接口，供外部使用

    public MemoAdapter(List<MemoData> list){
        this.memoDataList=list;
    }

    @NonNull
    @Override
    public memoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        memoHolder holder=new memoHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final memoHolder holder, final int position) {

        MemoData memoData=memoDataList.get(position);
        holder.bind(memoData);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(v,position);
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getItemSid(int pos){
        return memoDataList.get(pos).getmId();
    }

    @Override
    public int getItemCount() {
        return memoDataList.size();
    }

    public void setMemoDataList(List<MemoData> memoDataList) {
        this.memoDataList = memoDataList;
    }
}
