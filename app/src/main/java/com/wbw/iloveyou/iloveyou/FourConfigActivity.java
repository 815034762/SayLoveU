package com.wbw.iloveyou.iloveyou;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wbw.iloveyou.R;
import com.wbw.iloveyou.util.BitmapCache;
import com.wbw.iloveyou.util.SharedPreferencesXml;
import com.wbw.iloveyou.util.Util;
import com.wbw.iloveyou.view.PopConfigWindows;
import com.wbw.iloveyou.widget.shimmer.FlyTxtView;
import com.wbw.iloveyou.widget.shimmer.ShimmerFrameLayout;
import com.wbw.iloveyou.widget.svg.CustomShapeImageView;
import com.wbw.iloveyou.widget.svg.CustomShapeSquareImageView;


public class FourConfigActivity extends Activity {
	private Context mContext;
	private ShimmerFrameLayout sf_imageview;
	private RelativeLayout rl_middle_love;
	private ImageView iv_love_picture,iv_love_love;
	private CustomShapeImageView csi_top_left,csi_top_right;
	private CustomShapeSquareImageView csi_bottom_left,csi_bottom_right;
	private TextView tv_left_name,tv_right_name;
	private FlyTxtView ftv_say_love;
	
	
	
	private SharedPreferencesXml spxml;
	private String tips = "This is tip";
	public static String default_love = "I love U";
	public static String default_name = "惊不惊喜?";
	  @Override
	  protected void onCreate(Bundle savedInstanceState)
	  {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.fourview);
	    mContext  = this;
	    Util.init().setContext(mContext);
	    spxml = SharedPreferencesXml.init();
	    
	    findAllViews();
	    setDefalutValue();
	    createAction();
	  
    
	    
	  }
	  
	  public void findAllViews() {
			 sf_imageview = (ShimmerFrameLayout) findViewById(R.id.sf_imageview);
			 ftv_say_love = (FlyTxtView) findViewById(R.id.ftv_say_love1);
			 rl_middle_love = (RelativeLayout) findViewById(R.id.rl_middle_love);
			 iv_love_love = (ImageView) findViewById(R.id.iv_love_love);
			 iv_love_picture = (ImageView) findViewById(R.id.iv_love_picture);
			 csi_bottom_left = (CustomShapeSquareImageView) findViewById(R.id.csi_bottom_left);
			 csi_bottom_right = (CustomShapeSquareImageView) findViewById(R.id.csi_bottom_right);
			 csi_top_left = (CustomShapeImageView) findViewById(R.id.csi_top_left);
			 csi_top_right = (CustomShapeImageView) findViewById(R.id.csi_top_right);
			 tv_left_name = (TextView) findViewById(R.id.tv_left_name);
			 tv_right_name = (TextView) findViewById(R.id.tv_right_name);
			 
		}
		
		public void setDefalutValue(){		  
			Toast.makeText(mContext, tips, Toast.LENGTH_LONG).show();
		    sf_imageview.setBaseAlpha(0.5f);
		    sf_imageview.setDuration(2000);
		    sf_imageview.setRepeatDelay(500);
		    sf_imageview.startShimmerAnimation();
		    setLoveCenterPic();
		    setLoveTopLeftPic();
		    setLoveTopRightPic();
		    setLoveBottomLeftPic();
		    setLoveBottomRightPic();		    

			ftv_say_love.setTextSize(20);
			ftv_say_love.setTextColor(Color.GREEN);
			setFlyText();
			setLeftName();
			setRightName();
		}
		
		private void setLeftName(){
			String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_LEFT];
			String love_string = spxml.getConfigSharedPreferences(love_name,"");
			if(love_string!=null && !love_string.equals("")){
				tv_left_name.setText(love_string);
			}else{
				tv_left_name.setText(default_name);
			}
		}
		
		private void setRightName(){
			String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_RIGHT];
			String love_string = spxml.getConfigSharedPreferences(love_name,"");
			if(love_string!=null && !love_string.equals("")){
				tv_right_name.setText(love_string);
			}else{
				tv_right_name.setText(default_name);
			}
		}
		
		private void setFlyText(){
			String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_SYA_LOVE];
			String love_string = spxml.getConfigSharedPreferences(love_name,"");
			if(love_string!=null && !love_string.equals("")){
				ftv_say_love.setTexts(love_string);
			}else{
				ftv_say_love.setTexts(default_love);
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
			Bitmap love_centre_pic = getPic(PopConfigWindows.PIC_LOVE_LOVE_CENTER);
			if(love_centre_pic!=null){
				//iv_love_picture.setImageBitmap(love_centre_pic);
				Drawable draw = new BitmapDrawable(mContext.getResources(), love_centre_pic);
				iv_love_picture.setBackgroundDrawable(draw);
			}
		}		
		
		private void setLoveTopLeftPic(){
			Bitmap love_topleft_pic = getPic(PopConfigWindows.PIC_LOVE_TOP_LEFT);
			if(love_topleft_pic!=null) csi_top_left.setImageBitmap(love_topleft_pic);
		}
		private void setLoveTopRightPic(){
			Bitmap love_topright_pic = getPic(PopConfigWindows.PIC_LOVE_TOP_RIGHT);
			if(love_topright_pic!=null) csi_top_right.setImageBitmap(love_topright_pic);
		}
		private void setLoveBottomLeftPic(){
			Bitmap love_bottomleft_pic = getPic(PopConfigWindows.PIC_LOVE_BOTTOM_LEFT);
			if(love_bottomleft_pic!=null) csi_bottom_left.setImageBitmap(love_bottomleft_pic);
		}
		private void setLoveBottomRightPic(){
			Bitmap love_bottomright_pic = getPic(PopConfigWindows.PIC_LOVE_BOTTOM_RIGHT);
			if(love_bottomright_pic!=null) csi_bottom_right.setImageBitmap(love_bottomright_pic);
		}
		
		public void createAction(){
			 iv_love_love.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setLoaclBackPic(PopConfigWindows.PIC_LOVE_LOVE_CENTER);
				}
			});
			 csi_bottom_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setLoaclBackPic(PopConfigWindows.PIC_LOVE_BOTTOM_LEFT);
				}
			});
			csi_bottom_right.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setLoaclBackPic(PopConfigWindows.PIC_LOVE_BOTTOM_RIGHT);
				}
			});
			csi_top_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setLoaclBackPic(PopConfigWindows.PIC_LOVE_TOP_LEFT);
				}
			});
			csi_top_right.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setLoaclBackPic(PopConfigWindows.PIC_LOVE_TOP_RIGHT);
				}
			});
			ftv_say_love.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String def_string;
					String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_SYA_LOVE];
					String love_string = spxml.getConfigSharedPreferences(love_name,"");
					if(love_string!=null && !love_string.equals("")){
						def_string = love_string;
					}else{
						def_string = default_love;
					}
					PopConfigWindows popupWindow = new PopConfigWindows(mContext,handler,
							PopConfigWindows.TEXT_SYA_LOVE,null,def_string);
			        popupWindow.showAtLocation(iv_love_love, Gravity.CENTER
			                | Gravity.CENTER, 0, 0);
				}
			});
			tv_left_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String def_string;
					String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_LEFT];
					String love_string = spxml.getConfigSharedPreferences(love_name,"");
					if(love_string!=null && !love_string.equals("")){
						def_string = love_string;
					}else{
						def_string = default_name;
					}
					PopConfigWindows popupWindow = new PopConfigWindows(mContext,handler,
							PopConfigWindows.TEXT_NAME_LEFT,null,def_string);
			        popupWindow.showAtLocation(iv_love_love, Gravity.CENTER
			                | Gravity.CENTER, 0, 0);
				}
			});
			tv_right_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String def_string;
					String love_name = PopConfigWindows.NAMES[PopConfigWindows.TEXT_NAME_RIGHT];
					String love_string = spxml.getConfigSharedPreferences(love_name,"");
					if(love_string!=null && !love_string.equals("")){
						def_string = love_string;
					}else{
						def_string = default_name;
					}
					PopConfigWindows popupWindow = new PopConfigWindows(mContext,handler,
							PopConfigWindows.TEXT_NAME_RIGHT,null,def_string);
			        popupWindow.showAtLocation(iv_love_love, Gravity.CENTER
			                | Gravity.CENTER, 0, 0);
				}
			});
		}
	  
	 // private final int START_FLY = 10;
	    Handler handler = new Handler() {

	  	@Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
//	            case START_FLY:
//	            	   ftv_say_love.startAnimation();
//	            	break;
	            case PopConfigWindows.PIC_LOVE_LOVE_CENTER:
	            	setLoveCenterPic();
	            	break;
	            case PopConfigWindows.PIC_LOVE_BOTTOM_LEFT:
	            	setLoveBottomLeftPic();
	            	break;
	            case PopConfigWindows.PIC_LOVE_BOTTOM_RIGHT:
	            	setLoveBottomRightPic();
	            	break;
	            case PopConfigWindows.PIC_LOVE_TOP_LEFT:
	            	setLoveTopLeftPic();
	            	break;
	            case PopConfigWindows.PIC_LOVE_TOP_RIGHT:
	            	setLoveTopRightPic();
	            	break;
	            case PopConfigWindows.TEXT_SYA_LOVE:
	            	setFlyText();
	            	break;
	            case PopConfigWindows.TEXT_NAME_LEFT:
	            	setLeftName();
	            	break;
	            case PopConfigWindows.TEXT_NAME_RIGHT:
	            	setRightName();
	            	break;
	            }
	        }
	    };
	    
	  		public void setLoaclBackPic(int type){
	  			Intent intent = new Intent();
	  	        intent.setType("image/*");
	  	        intent.setAction(Intent.ACTION_GET_CONTENT);
	  	        startActivityForResult(intent, type);
	  		}
	  		
	  		
	  		@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				
			 if (resultCode == RESULT_OK && requestCode<99) {
					Uri uri = data.getData();
					 Intent intent = new Intent();
					intent.setAction("com.android.camera.action.CROP");  
	                intent.setDataAndType(uri, "image/*");
	                intent.putExtra("crop", "true");  
	                if(requestCode == PopConfigWindows.PIC_LOVE_LOVE_CENTER){
		                intent.putExtra("aspectX", 1);
		                intent.putExtra("aspectY", 1);  
		                intent.putExtra("outputX", 473);
		                intent.putExtra("outputY", 473); 
	                }else{
	                	intent.putExtra("aspectX", 1);
		                intent.putExtra("aspectY", 1);  
		                intent.putExtra("outputX", 240);
		                intent.putExtra("outputY", 240); 
	                }
	                intent.putExtra("return-data", true);  
	                  
	                FourConfigActivity.this.startActivityForResult(intent, requestCode+100);  
				
				}else if (resultCode == RESULT_OK && requestCode>99){
					int type = requestCode-100;
					 Bitmap bmap = data.getParcelableExtra("data");
						String base = Util.base;
						if(base.equals("")){
							 base  = Environment.getExternalStorageDirectory().getPath()+"/iloveyou";
						}
						String name=getName(type);
						String suffix = ".jpg";
						String pathString = base+"/"+name+suffix;
						saveBitmap(pathString,bmap);
						
						spxml.setConfigSharedPreferences(name,pathString);
						
						BitmapCache.getInstance().addCacheBitmap(bmap, name);
						
						handler.sendEmptyMessage(type);
				}
				super.onActivityResult(requestCode, resultCode, data);
			}   
	  		private String getName(int type){
	  			return PopConfigWindows.NAMES[type];
	  		}
	  		
	  		private void saveBitmap(final String path, final Bitmap bitmap){
	  			new Thread(new Runnable() {
	  				
	  				@Override
	  				public void run() {
	  					Util.init().writeFromInputToSD(path, bitmap);
	  				}
	  			}).start();
	  		}  
}
