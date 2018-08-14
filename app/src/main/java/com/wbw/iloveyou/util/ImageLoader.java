package com.wbw.iloveyou.util;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wbw.iloveyou.LoveApplication;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Create by Zhangty on 2018/8/13
 */
public class ImageLoader {


    public static void loadImageUrl(String url, ImageView imageView){
        Picasso.with(LoveApplication.getApplication()).load(Uri.parse(url)).centerCrop().memoryPolicy(NO_CACHE, NO_STORE).into(imageView);
    }

    public static void loadResizeImageUrl(String url,int resizeWidth,int resizeHeight, ImageView imageView){
        Picasso.with(LoveApplication.getApplication()).load(Uri.parse(url)).resize(resizeWidth,resizeHeight).centerCrop().memoryPolicy(NO_CACHE, NO_STORE).into(imageView);
    }
}
