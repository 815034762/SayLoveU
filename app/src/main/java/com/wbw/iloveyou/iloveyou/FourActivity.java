package com.wbw.iloveyou.iloveyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbw.iloveyou.LoveApplication;
import com.wbw.iloveyou.R;
import com.wbw.iloveyou.util.BitmapCache;
import com.wbw.iloveyou.util.ImageLoader;
import com.wbw.iloveyou.util.SharedPreferencesXml;
import com.wbw.iloveyou.util.Util;
import com.wbw.iloveyou.view.PopConfigWindows;
import com.wbw.iloveyou.widget.dk.ActivityAnimationTool;
import com.wbw.iloveyou.widget.dk.anim.SwitchAnimationUtil;
import com.wbw.iloveyou.widget.dk.anim.SwitchAnimationUtil.AnimationType;
import com.wbw.iloveyou.widget.shimmer.FlyTxtView;
import com.wbw.iloveyou.widget.shimmer.ShimmerFrameLayout;

public class FourActivity extends Activity {
	private Context mContext;
	private ShimmerFrameLayout sf_imageview;
	private RelativeLayout rl_middle_love;
	private ImageView iv_love_picture,iv_love_pink;
	private ImageView csi_top_left,csi_top_right;
	private ImageView csi_bottom_left,csi_bottom_right;
	private TextView tv_left_name,tv_right_name;
	private FlyTxtView ftv_say_love;
	private LinearLayout ll_name,ll_four;
	private SharedPreferencesXml spxml;

	private long lastclicktime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityAnimationTool.prepareAnimation(this);
		setContentView(R.layout.fourview);
		mContext  = this;
		Util.init().setContext(mContext);
		spxml = SharedPreferencesXml.init();

		findAllViews();

		setDefalutValue();
		bindAnimation(getWindow().getDecorView());
		handler.sendEmptyMessageDelayed(SHOWANIM, 3000);
		ActivityAnimationTool.animate(this, 800);
	}


	@Override
	protected void onStop() {
		ActivityAnimationTool.cancel(this);
		super.onStop();
	}

	private void bindAnimation(View view) {

		if (view instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) view;
			for (int i = 0; i < group.getChildCount(); i++) {
				bindAnimation(group.getChildAt(i));
			}

		} else {
			view.setAlpha(0);
		}
	}


	private void showAnim(){
		new SwitchAnimationUtil(2000,5000).startAnimation(sf_imageview, AnimationType.ALPHA);
		new SwitchAnimationUtil(1000,5000).startAnimation(rl_middle_love, AnimationType.ROTATE);
		new SwitchAnimationUtil(2000,5000).startAnimation(iv_love_pink, AnimationType.ALPHA);
		new SwitchAnimationUtil(2000,5000).startAnimation(ll_name, AnimationType.SCALE);
		handler.sendEmptyMessageDelayed(SHOWALPHA,2000);
		handler.sendEmptyMessageDelayed(SETFLYTEXT, 4000);
	}

	public void findAllViews() {

		sf_imageview =  findViewById(R.id.sf_imageview);
		ftv_say_love =  findViewById(R.id.ftv_say_love1);
		rl_middle_love = findViewById(R.id.rl_middle_love);
		iv_love_picture =  findViewById(R.id.iv_love_picture);
		csi_bottom_left =  findViewById(R.id.csi_bottom_left);
		csi_bottom_right =  findViewById(R.id.csi_bottom_right);
		csi_top_left =  findViewById(R.id.csi_top_left);
		csi_top_right =  findViewById(R.id.csi_top_right);
		tv_left_name =  findViewById(R.id.tv_left_name);
		tv_right_name = findViewById(R.id.tv_right_name);
		ll_name =  findViewById(R.id.ll_name);
		ll_four = findViewById(R.id.ll_four);
		iv_love_pink = findViewById(R.id.iv_love_pink);

		iv_love_picture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				BrowseImageViewActivity.start(FourActivity.this,LoveApplication.info.getFourImageCenter());
			}
		});

		csi_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				BrowseImageViewActivity.start(FourActivity.this,LoveApplication.info.getFourImage1());
			}
		});

		csi_top_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				BrowseImageViewActivity.start(FourActivity.this,LoveApplication.info.getFourImage2());
			}
		});

		csi_bottom_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				BrowseImageViewActivity.start(FourActivity.this,LoveApplication.info.getFourImage3());
			}
		});

		csi_bottom_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				BrowseImageViewActivity.start(FourActivity.this,LoveApplication.info.getFourImage4());
			}
		});
	}

	public void setDefalutValue(){

		sf_imageview.setBaseAlpha(0.5f);
		sf_imageview.setDuration(2000);
		sf_imageview.setRepeatDelay(500);

		setLoveCenterPic();
		setLoveTopLeftPic();
		setLoveTopRightPic();
		setLoveBottomLeftPic();
		setLoveBottomRightPic();

		ftv_say_love.setTextSize(20);
		ftv_say_love.setTextColor(getResources().getColor(R.color.four_string));

		setLeftName();
		setRightName();


		ll_four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if((System.currentTimeMillis()-lastclicktime)<=1500){
					lastclicktime = System.currentTimeMillis();
				}else{
					lastclicktime= System.currentTimeMillis();
				}
//				if(clicktimes>=2) gotoConfigActivity();
				handler.sendEmptyMessageDelayed(CACLICK, 4000);

			}
		});
	}

	private void setLeftName(){
		String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_LEFT];
		String love_string;

		if(LoveApplication.info != null){
			love_string = LoveApplication.info.getFourContent1();
		}else {
			love_string = spxml.getConfigSharedPreferences(love_name, "");
		}

		if(love_string!=null && !love_string.equals("")){
			tv_left_name.setText(love_string);
		}else{
			tv_left_name.setText(FourConfigActivity.default_name);
		}
	}

	private void setRightName(){
		String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_RIGHT];

		String love_string;

		if(LoveApplication.info != null){
			love_string = LoveApplication.info.getFourContent2();
		}else {
			love_string = spxml.getConfigSharedPreferences(love_name, "");
		}
		if(love_string!=null && !love_string.equals("")){
			tv_right_name.setText(love_string);
		}else{
			tv_right_name.setText(FourConfigActivity.default_name);
		}
	}

	private void setFlyText(){
		ftv_say_love.setAlpha(1);
		String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_SYA_LOVE];

		String love_string;

		if(LoveApplication.info != null){
			love_string = LoveApplication.info.getFourContent3();
		}else {
			love_string = spxml.getConfigSharedPreferences(love_name, "");
		}
		if(love_string!=null && !love_string.equals("")){
			ftv_say_love.setTexts(love_string);
		}else{
			ftv_say_love.setTexts(FourConfigActivity.default_love);
		}
		ftv_say_love.startAnimation();
	}

	private Bitmap getPic(int type){
		String love_name = PopConfigWindows.NAMES[type];
		String love_path = spxml.getConfigSharedPreferences(love_name,"");
		Bitmap love_pic = BitmapCache.getInstance().getBitmap(love_path, love_name);
		return love_pic;
	}



	private void setLoveCenterPic(){

		if(LoveApplication.info != null && !"".equals(LoveApplication.info.getFourImageCenter())){
			ImageLoader.loadResizeImageUrl(LoveApplication.info.getFourImageCenter(),530,473,iv_love_picture);
		}else {
			Bitmap love_centre_pic = getPic(PopConfigWindows.PIC_LOVE_LOVE_CENTER);
			if(love_centre_pic!=null){
				Drawable draw = new BitmapDrawable(mContext.getResources(), love_centre_pic);
				iv_love_picture.setBackgroundDrawable(draw);
			}
		}
	}

	private void setLoveTopLeftPic(){
		if(LoveApplication.info != null && !"".equals(LoveApplication.info.getFourImage1())){
			ImageLoader.loadResizeImageUrl(LoveApplication.info.getFourImage1(),80,80,csi_top_left);
		}else {
			Bitmap love_topleft_pic = getPic(PopConfigWindows.PIC_LOVE_TOP_LEFT);
			if(love_topleft_pic!=null) {
				csi_top_left.setImageBitmap(love_topleft_pic);
			}
		}
	}
	private void setLoveTopRightPic(){

		if(LoveApplication.info != null && !"".equals(LoveApplication.info.getFourImage2())){
			ImageLoader.loadResizeImageUrl(LoveApplication.info.getFourImage2(),80,80,csi_top_right);
		}else {
			Bitmap love_topright_pic = getPic(PopConfigWindows.PIC_LOVE_TOP_RIGHT);
			if(love_topright_pic!=null) {
				csi_top_right.setImageBitmap(love_topright_pic);
			}
		}
	}
	private void setLoveBottomLeftPic(){

		if(LoveApplication.info != null && !"".equals(LoveApplication.info.getFourImage3())){
			ImageLoader.loadResizeImageUrl(LoveApplication.info.getFourImage3(),80,80,csi_bottom_left);
		}else {
			Bitmap love_bottomleft_pic = getPic(PopConfigWindows.PIC_LOVE_BOTTOM_LEFT);
			if(love_bottomleft_pic!=null) {
				csi_bottom_left.setImageBitmap(love_bottomleft_pic);
			}
		}
	}
	private void setLoveBottomRightPic(){

		if(LoveApplication.info != null && !"".equals(LoveApplication.info.getFourImage4())){
			ImageLoader.loadResizeImageUrl(LoveApplication.info.getFourImage4(),80,80,csi_bottom_right);
		}else {
			Bitmap love_bottomright_pic = getPic(PopConfigWindows.PIC_LOVE_BOTTOM_RIGHT);
			if(love_bottomright_pic!=null) {
				csi_bottom_right.setImageBitmap(love_bottomright_pic);
			}
		}
	}

	private void gotoConfigActivity(){
		Intent t = new Intent();
		t.setClass(FourActivity.this, ConfigActivity.class);
		startActivity(t);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		FourActivity.this.finish();
		BitmapCache.getInstance().clearCache();
	}

	private final int SHOWALPHA = 0;
	private final int SHOWANIM = 1;
	private final int SETFLYTEXT = 3;
	private final int CACLICK = 7;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOWALPHA:
					sf_imageview.startShimmerAnimation();
					break;
				case SHOWANIM:
					showAnim();
					break;
				case SETFLYTEXT:
					setFlyText();
					break;
			}
		}
	};

}
