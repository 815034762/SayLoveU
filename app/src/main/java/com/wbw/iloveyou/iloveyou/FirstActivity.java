package com.wbw.iloveyou.iloveyou;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wbw.iloveyou.R;
import com.wbw.iloveyou.util.BitmapCache;
import com.wbw.iloveyou.util.CloseAction;
import com.wbw.iloveyou.util.MediaPlay;
import com.wbw.iloveyou.util.SharedPreferencesXml;
import com.wbw.iloveyou.util.Util;
import com.wbw.iloveyou.view.FirstSurfaceView;

import java.io.InputStream;

/**
 * 首页
 */
public class FirstActivity extends Activity {

    private final int SHOWHEART = 2;
    private final int SHOWXIN = 1;
    private final int SHOWZI = 3;
    private final int GOTOSECOND = 4;
    private final int SOUND = 5;
    FrameLayout f1;
    FirstSurfaceView fr;
    LinearLayout l1;
    private Context mContext;
    private int screen_h;
    private int screen_w;
    private SharedPreferencesXml spxml;
    private long lastclicktime = 0;
    private int clicktimes = 0;
    private MediaPlay me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.firstview);


        Display localDisplay = getWindowManager().getDefaultDisplay();
        this.screen_w = localDisplay.getWidth();
        this.screen_h = localDisplay.getHeight();
        this.mContext = getApplicationContext();

        Util.init().setContext(mContext);
        spxml = SharedPreferencesXml.init();

        me = MediaPlay.init();
        me.stop();
        String first_music = spxml.getConfigSharedPreferences("first_music", "0");
        if (first_music.equals("") || first_music.equals("0")) {
            me.InitMediaPlay(mContext, R.raw.heartv);
        } else {
            me.InitMediaPlay(mContext, first_music);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        findAllViews();
        createActions();
        spxml.setConfigSharedPreferences("firstrun", "false");
        spxml.setConfigSharedPreferences("firstconfig", "false");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void findAllViews() {
        f1 = ((FrameLayout) findViewById(R.id.f1));
        l1 = ((LinearLayout) findViewById(R.id.l1));
    }

    private void createActions() {
        String fb = spxml.getConfigSharedPreferences("first_back", "0");
        if (fb.equals("") || fb.equals("0")){
            l1.setBackgroundResource(R.drawable.q2);
        } else {
            try {
                Drawable draw = null;
                Uri uri = Uri.parse(fb);
                ContentResolver cr = mContext.getContentResolver();
                InputStream in = cr.openInputStream(uri);
                Bitmap bitmap = Util.init().getBitmap(in, 4);
                draw = new BitmapDrawable(mContext.getResources(), bitmap);
                in.close();
                l1.setBackgroundDrawable(draw);
            } catch (Exception e) {
                l1.setBackgroundResource(R.drawable.q2);
            }
        }
        this.fr = new FirstSurfaceView(this, this.screen_w, this.screen_h, handler);
        this.f1.removeAllViews();
        this.f1.addView(this.fr, new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        this.fr.showBackground();
        this.handler.sendEmptyMessageDelayed(SHOWXIN, 1500L);
        this.handler.sendEmptyMessageDelayed(SHOWHEART, 700L);
        this.handler.sendEmptyMessageDelayed(SHOWZI, 1000L);
        this.handler.sendEmptyMessageDelayed(SOUND, 800);
        f1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((System.currentTimeMillis() - lastclicktime) <= 1500) {
                    lastclicktime = System.currentTimeMillis();
                    clicktimes++;
                } else {
                    lastclicktime = System.currentTimeMillis();
                }
                if (clicktimes >= 2) {
                    goSecondActivity();
//                    gotoConfigAcitvity();
                }
                handler.sendEmptyMessageDelayed(CACLICK, 4000);

            }
        });
    }

    public void goSecond() {
        Intent t = new Intent();
        t.setClass(FirstActivity.this, SecondActivity.class);
        startActivity(t);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        FirstActivity.this.finish();
//        BitmapCache.getInstance().clearCache();
    }

    private final int NOCONFIG = 6;
    private final int CACLICK = 7;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                default:
                case SHOWXIN:
                    fr.showXin();
                    break;
                case SHOWHEART:
                    fr.showHeart();
                    break;
                case SHOWZI:
                    fr.showWenzi();
                    break;
                case GOTOSECOND:
                    goSecond();
                    break;
                case SOUND:
                    System.out.println("palyaa");
                    String m_onoff = spxml.getConfigSharedPreferences("music_on_off", "on");
                    if (!m_onoff.equals("off")) {
                        me.play();
                    }
                    break;
                case NOCONFIG:
                    isconfig = false;
                    break;
                case CACLICK:
                    clicktimes = 0;
                    break;
            }

        }
    };

    private boolean isconfig = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

//            if(isconfig){//不跳到设置页面
//                  goSecondActivity();
////                gotoConfigAcitvity();
//            }else{
            new CloseAction(FirstActivity.this, fr);
//            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!isconfig) {
                isconfig = true;
                handler.sendEmptyMessageDelayed(NOCONFIG, 2000);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void goSecondActivity() {
        fr.setRun(true);
        Intent t = new Intent();
        t.setClass(FirstActivity.this, SecondActivity.class);
        startActivity(t);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//      FirstActivity.this.finish();
//      BitmapCache.getInstance().clearCache();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    private float startX = 0;
    private float endX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                if ((endX - startX) > 80) {
                    goSecondActivity();
                    return true;
                }
                break;
        }
        return false;
    }
}