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
import com.wbw.iloveyou.view.ThridSurfaceView;
import com.wbw.iloveyou.widget.dk.ActivityAnimationTool;
import com.wbw.iloveyou.widget.dk.WaterEffect;

import java.io.InputStream;

public class ThridActivity extends Activity {
    FrameLayout f3;
    LinearLayout l3;
    private Context mContext;
    private int screen_h;
    private int screen_w;


    private SharedPreferencesXml spxml;
//    private MediaPlay me;
    private long lastclicktime = 0;
    private int clicktimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.thridview);
        Display localDisplay = getWindowManager().getDefaultDisplay();
        screen_w = localDisplay.getWidth();
        screen_h = localDisplay.getHeight();
        mContext = getApplicationContext();
        Util.init().setContext(mContext);
        spxml = SharedPreferencesXml.init();
    }

    @Override
    public void onResume() {
        super.onResume();
//        me = MediaPlay.init();
//        String thrid_music = spxml.getConfigSharedPreferences("thrid_music", "0");
//        if (thrid_music.equals("") || thrid_music.equals("0")) {
//            me.InitMediaPlay(mContext, R.raw.thrid_nan);
//        } else {
//            me.InitMediaPlay(mContext, thrid_music);
//        }
        findAllViews();
        createActions();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tsv.setRun(true);
//        me.stop();
    }

    private void findAllViews() {
        f3 = (FrameLayout) findViewById(R.id.f3);
        l3 = (LinearLayout) findViewById(R.id.l3);
    }

    private ThridSurfaceView tsv;

    private void createActions() {
        String tb = spxml.getConfigSharedPreferences("thrid_back", "0");
        if (tb.equals("") || tb.equals("0")) {
            l3.setBackgroundResource(R.drawable.q5);
        } else {
            try {
                Drawable draw = null;
                Uri uri = Uri.parse(tb);
                ContentResolver cr = mContext.getContentResolver();
                InputStream in = cr.openInputStream(uri);
                Bitmap bitmap = Util.init().getBitmap(in, 4);
                //ImageView imageView = (ImageView) findViewById(R.id.iv01);
                //imageView.setImageBitmap(bitmap);

                draw = new BitmapDrawable(mContext.getResources(), bitmap);
                in.close();
                l3.setBackgroundDrawable(draw);
            } catch (Exception e) {
                //draw = getLocalDraw();
                l3.setBackgroundResource(R.drawable.q5);
            }
        }
        //l3.setBackgroundResource(R.drawable.q5);
        tsv = new ThridSurfaceView(mContext, screen_w, screen_h, handler);
        f3.removeAllViews();
        f3.addView(tsv, new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        this.handler.sendEmptyMessageDelayed(SOUND, 1100);

        f3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((System.currentTimeMillis() - lastclicktime) <= 1500) {
                    lastclicktime = System.currentTimeMillis();
                    clicktimes++;
                } else {
                    lastclicktime = System.currentTimeMillis();
                }
                if (clicktimes >= 2) {
                    gotoFourActivity();
//						gotoConfigActivity();
                }
                handler.sendEmptyMessageDelayed(CACLICK, 4000);

            }
        });
    }

    private final int gotofour = 4;

    private final int SOUND = 5;
    private final int NOCONFIG = 6;
    private final int CACLICK = 7;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case gotofour:
                    gotoFourActivity();
                    break;
                case SOUND:
//                    String m_onoff = spxml.getConfigSharedPreferences("music_on_off", "on");
//                    if (!m_onoff.equals("off")){
//                        me.play();
//                    }
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
//				if(isconfig){
//					gotoFourActivity();
////				gotoConfigActivity();
//				}
//				else{
//					new CloseAction(ThridActivity.this,tsv);
//				}
//				return true;
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!isconfig) {
                isconfig = true;
                handler.sendEmptyMessageDelayed(NOCONFIG, 2000);
            }
            return true;
        } else{
            return super.onKeyDown(keyCode, event);
        }
    }

    private void gotoConfigActivity() {
        tsv.setRun(true);
        Intent t = new Intent();
        t.setClass(ThridActivity.this, ConfigActivity.class);
        startActivity(t);
        //overridePendingTransition(R.anim.slide_left_out,R.anim.slide_left_in);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ThridActivity.this.finish();
        BitmapCache.getInstance().clearCache();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }



    private void gotoFourActivity() {
        ActivityAnimationTool.init(new WaterEffect());
        ActivityAnimationTool.startActivity(ThridActivity.this,
                new Intent(ThridActivity.this, FourActivity.class));
        tsv.setRun(true);
//		 ThridActivity.this.finish();
    }
}
