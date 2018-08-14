package com.wbw.iloveyou.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;


public class BitmapCache {
    static private BitmapCache cache;
    private Hashtable<String, MySoftRef> hashRefs;
    private ReferenceQueue<Bitmap> q;

    private class MySoftRef extends SoftReference<Bitmap> {
        private String _key = "0";

        public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, String key) {
            super(bmp, q);
            _key = key;
        }
    }

    private BitmapCache() {
        hashRefs = new Hashtable<String, MySoftRef>();
        q = new ReferenceQueue<Bitmap>();
    }

    public static BitmapCache getInstance() {
        if (cache == null) {
            cache = new BitmapCache();
        }
        return cache;
    }

    public void addCacheBitmap(Bitmap bmp, String key) {
        cleanCache();
         MySoftRef ref = new MySoftRef(bmp, q, key);
        hashRefs.put(key, ref);
    }
    
    public Bitmap getCacheBitmap(String key){
    	   Bitmap bmp = null;
            if (hashRefs.containsKey(key)) {
               MySoftRef ref = (MySoftRef) hashRefs.get(key);
               bmp = (Bitmap) ref.get();
           }
            return bmp;
    }
    
    
    public Bitmap getBitmap(String path, String key_name){
    	Bitmap bmp = null;
    	bmp = getCacheBitmap(key_name);
    	if(bmp!=null)
    		return bmp;
    	if(path!=null && !path.equals("")){
    		bmp = Util.getBitmap(path);
    		addCacheBitmap(bmp,key_name);
    	}
    	return bmp;
    }

    public Bitmap getBitmap(int resId, Context context) {
        Bitmap bmp = null;
         if (hashRefs.containsKey(String.valueOf(resId))) {
            MySoftRef ref = (MySoftRef) hashRefs.get(String.valueOf(resId));
            bmp = (Bitmap) ref.get();
        }
         if (bmp == null) {
              bmp = BitmapFactory.decodeStream(context.getResources()
                    .openRawResource(resId));
            this.addCacheBitmap(bmp, String.valueOf(resId));
        }
        return bmp;
    }

    
    public Bitmap getBitmap(int resId, Context context, int w, int h) {
        Bitmap bmp = null;
         if (hashRefs.containsKey(String.valueOf(resId))) {
            MySoftRef ref = (MySoftRef) hashRefs.get(String.valueOf(resId));
            bmp = (Bitmap) ref.get();
        }
         if (bmp == null) {
              bmp = getBitmapByLM(resId, context, w, h);
            this.addCacheBitmap(bmp, String.valueOf(resId));
        }
        return bmp;
    }
    
   /**
    * @param resid
    * @param context
    * @param w
    * @param h
    * @return
    */
	public static Bitmap getBitmapByLM(int resid , Context context, int w, int h){
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = false;
		Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resid);
		if(bit == null){
			return null;
		}
		float realw = bit.getWidth();
		float realh = bit.getHeight();
		int WIDTH_NEED,HEIGHT_NEED;
		
			WIDTH_NEED = w;
			HEIGHT_NEED = h;
		
		int scalew = (int) (realw/WIDTH_NEED);
		int scaleh = (int) (realh/HEIGHT_NEED);
		int scale = (scalew>scaleh?scalew:scaleh);
		if(scale < 1) scale = 1;
		
		op.inPreferredConfig = Bitmap.Config.RGB_565;
	    op.inPurgeable = true;   
	    op.inInputShareable = true; 
	    op.inSampleSize = scale;
	    
	    bit = new BitmapFactory().decodeResource(context.getResources(), resid,op);
	    return bit;
	}
	
	 /**
	    * @param resid
	    * @param context
	    * @param n 
	    * @return
	    */
		public static Bitmap getBitmapByLM(int resid , Context context, int n){
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inJustDecodeBounds = false;
			Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resid);
			if(bit == null){
				
				return null;
			}
			int scale = n;
			if(scale < 1) scale = 1;
			
			
			op.inPreferredConfig = Bitmap.Config.RGB_565;
		    op.inPurgeable = true;   
		    op.inInputShareable = true; 
		    op.inSampleSize = scale;
		    
		    bit = new BitmapFactory().decodeResource(context.getResources(), resid,op);
		    return bit;
		}
	
    
    private void cleanCache() {
        MySoftRef ref = null;
        while ((ref = (MySoftRef) q.poll()) != null) {
            hashRefs.remove(ref._key);
        }
    }
    
    public void deleteByID(String key){
    	if(hashRefs.containsKey(key)){
    		hashRefs.remove(key);
    		cleanCache();
    		//q
    	}
    }

    public void clearCache() {

        cleanCache();
        //int i=0;
        Enumeration<String> en = hashRefs.keys();
        while (en.hasMoreElements()) {
        	String s = en.nextElement();
        	hashRefs.get(s).get().recycle();
        }
   
        hashRefs.clear();

        System.gc();
        System.runFinalization();
    }
    
    public int getCount(){
    	return hashRefs.size();
    }
}