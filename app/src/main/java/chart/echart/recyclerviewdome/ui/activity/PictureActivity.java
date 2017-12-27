package chart.echart.recyclerviewdome.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;
import chart.echart.recyclerviewdome.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends AppCompatActivity {
    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";

    private String img_url;
    private String img_desc;

    private ImageView iv_meizhi_pic;
    private FloatingActionButton save_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pic);
        parseIntent();
        inintView();

        Log.e("==",iv_meizhi_pic+"");
        //设置共享元素
        ViewCompat.setTransitionName(iv_meizhi_pic, TRANSIT_PIC);
        Glide.with(this).load(img_url).centerCrop().into(iv_meizhi_pic);
        new PhotoViewAttacher(iv_meizhi_pic);
    }
    //绑定组件，监听组件
    private void inintView(){
        iv_meizhi_pic = (ImageView) findViewById(R.id.iv_meizhi_pic);
        save_img = (FloatingActionButton)findViewById(R.id.save_img);
        save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });
    }
    private void parseIntent(){
        img_url = getIntent().getStringExtra(IMG_URL);
        img_desc = getIntent().getStringExtra(IMG_DESC);
    }
    public static Intent newIntent(Context context, String url, String desc){
        Intent intent = new Intent(context,PictureActivity.class);
        intent.putExtra(PictureActivity.IMG_URL,url);
        intent.putExtra(PictureActivity.IMG_DESC,desc);
        return intent;
    }
    //将图片下载到本地
    private void saveImage(){
        iv_meizhi_pic.buildDrawingCache();
        Bitmap bitmap = iv_meizhi_pic.getDrawingCache();
        //将Bitmap 转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream);
        byte[] byteArray = stream.toByteArray();
        File dir=new File(Environment.getExternalStorageDirectory ().getAbsolutePath()+"/zhigan" );
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, img_desc + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();
            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PictureActivity.this.sendBroadcast(intent);
            Toast.makeText(PictureActivity.this,"保存成功~",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
