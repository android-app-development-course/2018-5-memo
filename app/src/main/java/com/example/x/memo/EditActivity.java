package com.example.x.memo;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.memo.Base.MemoData;
import com.example.x.memo.Base.MemoList;
import com.example.x.memo.Base.display_text;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;
import com.xw.repo.BubbleSeekBar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

import static com.example.x.memo.R.color.bright_foreground_disabled_material_dark;
import static com.example.x.memo.R.color.red;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private BubbleSeekBar bubbleSeekBar;
    private ImageView imageView;
    private Dialog dialog;
    private TextView DateText;
    private TextView TimeText;
    private EditText Text;
    private ImageButton img_play;
    private int TextSize=20;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private String filePath = Environment.getExternalStorageDirectory()+"/MEMO/";
    private String filePath1 = Environment.getExternalStorageDirectory()+"/MEMO";
    private String FILEPATH=Environment.getExternalStorageDirectory()+"/MEMO/";
    private Boolean is_playing=false;
    private static final int RECORD=0;
    private static final int PHOTO = 1;

    private MemoData memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_edit);
        //判断文件夹是否已经生成
        File file1=new File(filePath1);
        if(!file1.exists())
        {
            file1.mkdirs();
        }
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String i=(String)getIntent().getSerializableExtra("edit");
        memo=MemoList.get(EditActivity.this).getMemo(i);

        DateText=(TextView)this.findViewById(R.id.date);
        TimeText=(TextView)this.findViewById(R.id.time);
        Text=(EditText)this.findViewById(R.id.edit);
        img_play=(ImageButton)this.findViewById(R.id.play);
        img_play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        img_play.setOnClickListener(this);
        img_play.setVisibility(View.INVISIBLE);

        imageView=(ImageView)this.findViewById(R.id.image_v);
        DateText.setText(memo.getDateF());
        TimeText.setText(memo.getDateT());
        Text.setText(memo.getmContext());
        filePath =filePath+memo.getmId()+"recorded_audio.wav";
        //memo.set;
        if(memo.isVIC())
        {
            String audio_dir=memo.getmVicPath();
            filePath=audio_dir;
            img_play.setVisibility(View.VISIBLE);
        }
        if(memo.isPIC())
        {
            String pic_dir=memo.getmPicPath();
            File file=new File(pic_dir);

            if(file!=null){
                Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        }

        //dialog更改字号
        AlertDialog.Builder builder = new AlertDialog.Builder(
                new android.view.ContextThemeWrapper(this,R.style.AppTheme)
        );
        View view = LayoutInflater.from(this).inflate(R.layout.bubble,null);
        builder.setView(view);
        dialog = builder.create();
        bubbleSeekBar=(BubbleSeekBar)view.findViewById(R.id.seek_bar);
        bubbleSeekBar.bringToFront();
        display_text my_display_text=new display_text();
        my_display_text.onCustomize(5,new SparseArray<String>());
        bubbleSeekBar.setCustomSectionTextArray(my_display_text);
        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                switch (progress)
                {
                    case 1:
                    {
                        TextSize=10;
                        Text.setTextSize(TextSize);break;
                    }
                    case 2:
                    {
                        TextSize=15;
                        Text.setTextSize(TextSize);break;
                    }
                    case 3:
                    {
                        TextSize=20;
                        Text.setTextSize(TextSize);break;
                    }
                    case 4:
                    {
                        TextSize=30;
                        Text.setTextSize(TextSize);break;
                    }
                    case 5:
                    {
                        TextSize=40;
                        Text.setTextSize(TextSize);break;
                    }

                }
            }
        });

        //功能按钮
        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("字体大小", getResources().getDrawable(R.drawable.mul_tx), 30);
        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("录音", getResources().getDrawable(R.drawable.mul_vic), 90);
        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("选择图片", getResources().getDrawable(R.drawable.mul_pic), 150);
        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);
        buttonItems.add(item3);

        MultiChoicesCircleButton multiChoicesCircleButton = (MultiChoicesCircleButton) findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);
        multiChoicesCircleButton.setItemRadius(80);

        multiChoicesCircleButton.setTextColor(R.color.transparent);
        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                switch(item.getText())
                {
                    case "字体大小": {
                        dialog.show();break;
                    }
                    case "录音":{
                        recordAudio(EditActivity.this.getCurrentFocus());
                        break;
                    }
                    case "选择图片":{

                        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                        getImage.addCategory(Intent.CATEGORY_OPENABLE);
                        getImage.setType("image/*");
                        startActivityForResult(getImage, PHOTO);
                        break;
                    }
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        memo.setmContext(Text.getText().toString());
        MemoList.get(EditActivity.this).updateMemo(memo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.play:
            {
                if(is_playing==false)
                {play1(filePath);is_playing=true;
                    img_play.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    img_play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                else
                {
                    mediaPlayer.stop();is_playing=false;
                    img_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    img_play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                break;
            }
        }
    }

    public void recordAudio(View v) {
        int color = getResources().getColor(R.color.red);
        int requestCode = 0;
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(filePath)
                .setColor(color)
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(true)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO:
            {
                if(resultCode==RESULT_OK){
                    Uri originalUri = data.getData();
                    ContentResolver resolver = getContentResolver();

                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));

                        savePicture(bitmap,memo.getmId()+"_pic.jpg");
                        memo.setmPicPath(FILEPATH+memo.getmId()+"_pic.jpg");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
            }
            case RECORD:
            {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(EditActivity.this,"FINISHED",Toast.LENGTH_SHORT).show();
                    img_play.setVisibility(View.VISIBLE);

                    memo.setmVicPath(filePath);

                } else if (resultCode == RESULT_CANCELED) {
                    File f=new File(filePath);
                    f.delete();
                    Toast.makeText(EditActivity.this,"CANCELED",Toast.LENGTH_SHORT).show();

                }
            }
        }

    }
    public void savePicture(Bitmap bm, String fileName) {
        if (null == bm) {
            return;
        }
        File foder = new File(FILEPATH);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            //压缩保存到本地
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play1(String path) {
        try {
            File f=new File(path);
            if(f.exists())
            {
                //        重置音频文件，防止多次点击会报错
                mediaPlayer.reset();
//        调用方法传进播放地址
                mediaPlayer.setDataSource(path);
//            异步准备资源，防止卡顿
                mediaPlayer.prepareAsync();
//            调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        img_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                        img_play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                });
            }
            else throw new IOException();

        } catch (IOException e) {
            img_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
            img_play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Toast.makeText(EditActivity.this,"播放失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
