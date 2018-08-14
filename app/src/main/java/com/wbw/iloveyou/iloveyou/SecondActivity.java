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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wbw.iloveyou.R;
import com.wbw.iloveyou.util.BitmapCache;
import com.wbw.iloveyou.util.CloseAction;
import com.wbw.iloveyou.util.MediaPlay;
import com.wbw.iloveyou.util.SharedPreferencesXml;
import com.wbw.iloveyou.util.Util;
import com.wbw.iloveyou.view.SecondSurfaceView;

import java.io.InputStream;

public class SecondActivity extends Activity {

    FrameLayout f2;
    LinearLayout l2;
    private Context mContext;
    private int screen_h;
    private int screen_w;

    private long lastclicktime = 0;
    private int clicktimes = 0;

    private SharedPreferencesXml spxml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.secondview);
        Display localDisplay = getWindowManager().getDefaultDisplay();
        screen_w = localDisplay.getWidth();
        screen_h = localDisplay.getHeight();
        mContext = getApplicationContext();
        Util.init().setContext(mContext);
        spxml = SharedPreferencesXml.init();
    }

    private void findAllViews() {
        f2 = (FrameLayout) findViewById(R.id.f2);
        l2 = (LinearLayout) findViewById(R.id.l2);
    }

    @Override
    public void onResume() {
        super.onResume();

        findAllViews();
        createActions();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private SecondSurfaceView ssv;

    private void createActions() {
        //float al = 0.5f;
        String fb = spxml.getConfigSharedPreferences("second_back", "0");
        if (fb.equals("") || fb.equals("0")){
            l2.setBackgroundResource(R.drawable.q6);
        } else {
            try {
                Drawable draw = null;
                Uri uri = Uri.parse(fb);
                ContentResolver cr = mContext.getContentResolver();
                InputStream in = cr.openInputStream(uri);
                Bitmap bitmap = Util.init().getBitmap(in, 4);

                draw = new BitmapDrawable(mContext.getResources(), bitmap);
                in.close();
                l2.setBackgroundDrawable(draw);
            } catch (Exception e) {
                //draw = getLocalDraw();
                l2.setBackgroundResource(R.drawable.q6);
            }
        }
        ssv = new SecondSurfaceView(this, this.screen_w, this.screen_h, handler);
        f2.removeAllViews();
        f2.addView(ssv, new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        handler.sendEmptyMessageDelayed(showh, 500L);
        handler.sendEmptyMessageDelayed(showtext, 1500L);
        handler.sendEmptyMessageDelayed(showb, 500L);

        f2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((System.currentTimeMillis() - lastclicktime) <= 1500) {
                    lastclicktime = System.currentTimeMillis();
                    clicktimes++;
                } else {
                    lastclicktime = System.currentTimeMillis();
                }
                if (clicktimes >= 2) {
                    goThrid();
                }
                handler.sendEmptyMessageDelayed(CACLICK, 4000);

            }
        });
    }

    public void goThrid() {
        Intent t = new Intent();
        t.setClass(SecondActivity.this, ThridActivity.class);
        startActivity(t);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

        ssv.setRun(true);
//       BitmapCache.getInstance().clearCache();
//       MediaPlay.init().stop();
    }


    private final int showh = 1;
    private final int showtext = 2;
    private final int showb = 3;
    private final int gotothrid = 4;
    private final int NOCONFIG = 6;
    private final int CACLICK = 7;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                default:
                case showh:
                    ssv.showh();
                    break;
                case showtext:
                    ssv.showW();
                    break;
                case showb:
                    ssv.showBK();
                    break;
                case gotothrid:
                    goThrid();
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("ss:" + f2.getWidth() + " ss_h:" + f2.getHeight());
    }

    private void gotoConfigActivity() {
        ssv.setRun(true);
        Intent t = new Intent();
        t.setClass(SecondActivity.this, ConfigActivity.class);
        startActivity(t);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        SecondActivity.this.finish();
        BitmapCache.getInstance().clearCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ssv.setRun(true);
    }

    private boolean isconfig = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SecondActivity.this.finish();
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

}
