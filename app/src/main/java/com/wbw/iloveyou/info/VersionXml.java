package com.wbw.iloveyou.info;

import android.util.Xml;

import com.wbw.iloveyou.util.Util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;


public class VersionXml {
	private  static VersionXml versionxml = null;
	public static VersionXml init(){
		if(versionxml == null)
			versionxml = new VersionXml();
		return versionxml;
	}
	
	
	/**
	 * @param path
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public void  getApkVersionXml(String path) throws XmlPullParserException, IOException {
		
		InputStream in = Util.init().getInputStream(path);
		ApkVersionInfo info = ApkVersionInfo.downloadinfo;
		
		XmlPullParser parser = Xml.newPullParser();
		//list.clear();
		
		parser.setInput(in, "UTF-8");
		
		int event = parser.getEventType();
		
		while(event!= XmlPullParser.END_DOCUMENT){
			switch(event){
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					 if(name.equals("versioncode")){
						info.setVersionCode(parser.nextText());
					}
					else if(name.equals("apkurl")){
						info.setApkUrl(parser.nextText());
					}
					else if(name.equals("description"))
						info.setUpdataDescription(parser.nextText());
					
					break;
				case XmlPullParser.END_TAG:
					break;
			}
			event = parser.next();				
		}	
		in.close();
	}
	


}


