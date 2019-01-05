package com.example.x.memo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.x.memo.Base.MemoData;
import com.example.x.memo.Base.MemoList;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<MemoData> memoDataList;
    private MemoAdapter memoAdapter;
    private SwipeMenuRecyclerView recyclerView;

    private String[] Permissions=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//View.SYSTEM_UI_FLAG_LAYOUT_STABLE   View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.memo_Rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        memoDataList=MemoList.get(MainActivity.this).getMemos();

        ImageButton search=(ImageButton)findViewById(R.id.search);
        search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(MainActivity.this,LockActivity.class);
                MainActivity.this.startActivity(intent);
                return true;
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemoData memoData = new MemoData();
                MemoList.get(MainActivity.this).addMemo(memoData);
                String i = memoData.getmId();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                MainActivity.this.startActivity(intent);
            }
        });

        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            initMemo();
        }
        */
        checkPermission();
        initMemo();

        memoAdapter.setListener(new MemoAdapter.onclickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String i = memoAdapter.getItemSid(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                MainActivity.this.startActivity(intent);
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
                MemoList.get(MainActivity.this).deleteMemo(s);
                memoDataList=MemoList.get(MainActivity.this).getMemos("false");
                memoAdapter.setMemoDataList(memoDataList);
                memoAdapter.notifyItemRemoved(position);
            }
        });
    }

    private void checkPermission(){
        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < Permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(Permissions[i]);
            }
        }
        if (!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMemo();
    }

    private void initMemo() {

        memoDataList=MemoList.get(MainActivity.this).getMemos("false");
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
