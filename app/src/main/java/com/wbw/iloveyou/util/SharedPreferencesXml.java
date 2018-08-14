package com.wbw.iloveyou.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wbw.iloveyou.R;

/**
 * 缓存
 */
public class SharedPreferencesXml {

	public SharedPreferences sp_config;
	public static String perference_m;
	private Context mContext;
	private BASE64Decoder decode;
	private BASE64Encoder encode;
	
	
	public static SharedPreferencesXml sp_xml;
	public static SharedPreferencesXml init(){
		if(sp_xml == null) {
			sp_xml = new SharedPreferencesXml();
		}
		return sp_xml;
	}
	
	public SharedPreferencesXml(){
		mContext = Util.init().getContext();
		decode = new BASE64Decoder();
		encode = new BASE64Encoder();
		if(sp_config == null){
			sp_config = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
	}
	

	
	/**
	 * @param key
	 * @param value
	 */
	public void setConfigSharedPreferences(String key, String value){
		String key_en,value_en;
		key_en = encode.encode(key.getBytes());
		value_en = encode.encode(value.getBytes());
		sp_config.edit().putString(key_en, value_en).commit();
	}

	public void setPhone(String phone){

		sp_xml.setConfigSharedPreferences("phone", phone);
	}

	public String getPhone(){

		return sp_xml.getConfigSharedPreferences("phone", "");
	}

	
	/**
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public String getConfigSharedPreferences(String key, String defaultvalue){
		
		String key_en = encode.encode(key.getBytes());
		String getvalue = sp_config.getString(key_en, defaultvalue);
		if(getvalue.equals(defaultvalue))
			return getvalue;
		
		try {
			String getvalue_de = new String(decode.decode(getvalue.getBytes()),"UTF-8");
			return getvalue_de;
		} catch (Exception e) {
			e.printStackTrace();
			return defaultvalue;
		}
		
	}
	
	/**
	 * @param key
	 * @param id
	 * @return
	 */
	public String getConfigSharedPreferences(String key, int id){
		
		String key_en = encode.encode(key.getBytes());
		String defaultvalue = getResourceString(id);
		String getvalue = sp_config.getString(key_en, defaultvalue);
		if(getvalue.equals(defaultvalue)){
			return getvalue;
		}
		try {
			String getvalue_de = new String(decode.decode(getvalue.getBytes()),"UTF-8");
			return getvalue_de;
		} catch (Exception e) {
			e.printStackTrace();
			return defaultvalue;
		}
		
	}
	
	public String getResourceString(int id){
		return mContext.getResources().getString(id);
	}
	
	
	public void setDefault(){
		sp_xml.setConfigSharedPreferences("first_name_1", getResourceString(R.string.first_et_1));
		sp_xml.setConfigSharedPreferences("first_name_2", getResourceString(R.string.first_et_2));
		sp_xml.setConfigSharedPreferences("second_words", getResourceString(R.string.second_words));
		sp_xml.setConfigSharedPreferences("thrid_f_et_1", getResourceString(R.string.thrid_f_et_1));
		sp_xml.setConfigSharedPreferences("thrid_f_et_2", getResourceString(R.string.thrid_f_et_2));
		sp_xml.setConfigSharedPreferences("thrid_s_et_1", getResourceString(R.string.thrid_s_et_1));
		sp_xml.setConfigSharedPreferences("thrid_s_et_2", getResourceString(R.string.thrid_s_et_2));
		sp_xml.setConfigSharedPreferences("thrid_s_et_3", getResourceString(R.string.thrid_s_et_3));
		sp_xml.setConfigSharedPreferences("thrid_s_et_4", getResourceString(R.string.thrid_s_et_4));
		sp_xml.setConfigSharedPreferences("first_back", "0");
		sp_xml.setConfigSharedPreferences("first_music", "0");
		sp_xml.setConfigSharedPreferences("second_back", "0");
		sp_xml.setConfigSharedPreferences("thrid_back", "0");
		sp_xml.setConfigSharedPreferences("thrid_music", "0");
		sp_xml.setConfigSharedPreferences("music_on_off", "on");
		 int C =  mContext.getResources().getColor(R.color.huang);
		sp_xml.setConfigSharedPreferences("second_color", String.valueOf(C));
	}
	

}
