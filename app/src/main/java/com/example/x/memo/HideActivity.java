package com.example.x.memo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.x.memo.Base.MemoData;
import com.example.x.memo.Base.MemoList;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.List;


public class HideActivity extends AppCompatActivity {

    public List<MemoData> memoDataList;
    private MemoAdapter memoAdapter;
    private SwipeMenuRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//View.SYSTEM_UI_FLAG_LAYOUT_STABLE   View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.memo_Rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemoData memoData = new MemoData();
                memoData.setmHide("true");
                MemoList.get(HideActivity.this).addMemo(memoData);
                String i = memoData.getmId();
                Intent intent = new Intent(HideActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                HideActivity.this.startActivity(intent);
            }
        });

        initMemo();

        memoAdapter.setListener(new MemoAdapter.onclickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String i = memoAdapter.getItemSid(position);
                Intent intent = new Intent(HideActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                HideActivity.this.startActivity(intent);
            }
        });

        recyclerView.setItemViewSwipeEnabled(true);
        recyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return false;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                // 此方法在Item在侧滑删除时被调用。
                int position = srcHolder.getAdapterPosition();
                String s=memoAdapter.getItemSid(position);
                MemoList.get(HideActivity.this).deleteMemo(s);
                memoDataList=MemoList.get(HideActivity.this).getMemos("true");
                memoAdapter.setMemoDataList(memoDataList);
                memoAdapter.notifyItemRemoved(position);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        initMemo();
    }

    private void initMemo() {

        memoDataList=MemoList.get(HideActivity.this).getMemos("true");
        if(memoAdapter==null)
        {
            memoAdapter=new MemoAdapter(memoDataList);
            recyclerView.setAdapter(memoAdapter);
        }
        else {
            memoAdapter.setMemoDataList(memoDataList);
            memoAdapter.notifyDataSetChanged();
        }
    }
}
