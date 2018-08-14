package com.wbw.iloveyou.iloveyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import com.wbw.iloveyou.R;
import cn.bluemobi.dylan.photoview.library.PhotoView;

public class BrowseImageViewActivity extends Activity {

    private String imgUrl = "";
    private PhotoView photoView;

    /**
     * 启动Activity
     * @param context
     * @param imgUrl
     */
    public static void start(Context context,String imgUrl){

        if(null == imgUrl || "".equals(imgUrl)){
            return;
        }
        Intent intent = new Intent(context, BrowseImageViewActivity.class);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        imgUrl = getIntent().getStringExtra("imgUrl");
        setContentView(R.layout.activity_browse_image_view);
        photoView = findViewById(R.id.iv_photo);

        Picasso.with(this)
                .load(imgUrl)
                .into(photoView);
    }
}
