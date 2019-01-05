package com.example.x.memo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.BubbleSeekBar;

public class LockActivity extends Activity {

   private int pw1,pw2,pw3,ToF=0;
   private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageDrawable(getDrawable(R.drawable.lock));

        sharedPreferences=getSharedPreferences("PW",Context.MODE_PRIVATE);
        ToF=sharedPreferences.getInt("ToF",0);
        if(ToF==0)
        {
            TextView textView=(TextView)findViewById(R.id.textView);
            textView.setText("请输入您的新密码");
        }
        BubbleSeekBar B1,B2,B3;
        B1=(BubbleSeekBar)findViewById(R.id.pw1);
        B2=(BubbleSeekBar)findViewById(R.id.pw2);
        B3=(BubbleSeekBar)findViewById(R.id.pw3);

        B1.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                pw1=progress;
            }
        });
        B2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                pw2=progress;

            }
        });
        B3.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                pw3=progress;
                TestPw();
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.finish();
    }

    private void TestPw(){
        if(ToF==0)
        {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("ToF",1);
            editor.putInt("PW1",pw1);
            editor.putInt("PW2",pw2);
            editor.putInt("PW3",pw3);
            editor.apply();
            //Go
            Intent intent=new Intent(LockActivity.this,HideActivity.class);
            LockActivity.this.startActivity(intent);
        }
        else {
            int PW1=0,PW2=0,PW3=0;
            PW1=sharedPreferences.getInt("PW1",-1);
            PW2=sharedPreferences.getInt("PW2",-1);
            PW3=sharedPreferences.getInt("PW3",-1);
            if(PW1==pw1&&PW2==pw2&&PW3==pw3)
            {
                //GO
                Intent intent=new Intent(LockActivity.this,HideActivity.class);
                LockActivity.this.startActivity(intent);
            }else {
                Toast.makeText(LockActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
