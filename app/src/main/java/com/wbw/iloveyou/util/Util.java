package com.wbw.iloveyou.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 */
public class Util {
	private static Util util;
	
	public static String base="";

	public static Util init() {
		if (util == null)
			util = new Util();
		return util;
	}

	private Util() {

	}
	
	private Context context = null;
	public void setContext(Context c){
		this.context = c;
	}
	public Context getContext(){
		return context;
	}

	/**
	 * @return File
	 */
	public File creatFileIfNotExist(String path) {
		System.out.println("cr");
		File file = new File(path);
		if (!file.exists()) {
			try {
				new File(path.substring(0, path.lastIndexOf(File.separator)))
						.mkdirs();
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return file;
	}

	/**
	 * @return File
	 */
	public File creatDirIfNotExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.mkdirs();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return file;
	}

	/**
	 * @param path
	 * @return
	 */
	public boolean IsExist(String path) {
		File file = new File(path);
		if (!file.exists())
			return false;
		else
			return true;
	}

	/**
	 * @param path
	 * @return
	 */
	public File creatNewFile(String path) {
		File file = new File(path);
		if (IsExist(path))
			file.delete();
		creatFileIfNotExist(path);
		return file;
	}

	/**
	 * @param path
	 * @return
	 */
	public boolean deleteFile(String path) {
		File file = new File(path);
		if (IsExist(path))
			file.delete();
		return true;
	}

	public boolean deleteFileDir(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!IsExist(path)) {
			return flag;
		}
		if (!file.isDirectory()) {

			file.delete();
			return true;
		}
		String[] filelist = file.list();
		File temp = null;
		for (int i = 0; i < filelist.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + filelist[i]);
			} else {
				temp = new File(path + File.separator + filelist[i]);
			}
			if (temp.isFile()) {

				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteFileDir(path + "/" + filelist[i]);
			}
		}
		file.delete();

		flag = true;
		return flag;
	}

	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	public String[] getFlieName(String rootpath) {
		File root = new File(rootpath);
		File[] filesOrDirs = root.listFiles();
		if (filesOrDirs != null) {
			String[] dir = new String[filesOrDirs.length];
			int num = 0;
			for (int i = 0; i < filesOrDirs.length; i++) {
				if (filesOrDirs[i].isDirectory()) {
					dir[i] = filesOrDirs[i].getName();

					num++;
				}
			}
			String[] dir_r = new String[num];
			num = 0;
			for (int i = 0; i < dir.length; i++) {
				if (dir[i] != null && !dir[i].equals("")) {
					dir_r[num] = dir[i];
					num++;
				}
			}
			return dir_r;
		} else
			return null;
	}

	/**
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public BufferedWriter getWriter(String path) throws FileNotFoundException,
            UnsupportedEncodingException {
		FileOutputStream fileout = null;
		fileout = new FileOutputStream(new File(path));
		OutputStreamWriter writer = null;
		writer = new OutputStreamWriter(fileout, "UTF-8");
		BufferedWriter w = new BufferedWriter(writer);
		return w;
	}

	public InputStream getInputStream(String path) throws FileNotFoundException {
		FileInputStream filein = null;
		File file = new File(path);
		filein = new FileInputStream(file);
		BufferedInputStream in = null;
		if (filein != null)
			in = new BufferedInputStream(filein);
		return in;
	}

	public boolean StateXmlControl(String path) {
		File f = new File(path);
		if (!f.exists())
			return false;
		if (f.length() == 0)
			return false;
		return true;
	}

	/**
	 * InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[6 * 1024];
		int count = -1;
		while ((count = in.read(data, 0, 4 * 1024)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return outStream.toByteArray();
	}

	/**
	 *
	 * @param in
	 *            OutputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] OutputStreamTOByte(OutputStream out)
			throws IOException {

		byte[] data = new byte[6 * 1024];
		out.write(data);
		return data;
	}

	/**
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteTOInputStream(byte[] in) {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static OutputStream byteTOOutputStream(byte[] in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(in);
		return out;
	}

	/**
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public File writeFromInputToSD(String path, Bitmap bitmap) {
		File file = null;
		OutputStream output = null;
		try {
			file = creatFileIfNotExist(path);
			output = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public File writeFromInputToSD(String path, InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		try {
			file = creatFileIfNotExist(path);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4 * 1024];
			int temp;
			while ((temp = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public File writeFromInputToSD(String path, byte[] b) {
		File file = null;
		OutputStream output = null;
		try {
			file = creatFileIfNotExist(path);
			output = new FileOutputStream(file);
			output.write(b);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 */
	public void saveTxtFile(String filePath, String text) {
		try {
			creatFileIfNotExist(filePath);
			String txt = readTextLine(filePath);
			text = text + txt;
			FileOutputStream out = new FileOutputStream(filePath);
			OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
			writer.write(text);
			writer.close();
			out.close();
		} catch (Exception e) {
		}

	}

	public void clearTxtFile(String filePath) {
		try {
			String text = "";
			FileOutputStream out = new FileOutputStream(filePath);
			OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
			writer.write(text);
			writer.close();
			out.close();
		} catch (Exception e) {
		}
	}

	public String readTextLine(String textFile) {
		try {
			FileInputStream input = new FileInputStream(textFile);
			InputStreamReader streamReader = new InputStreamReader(input,
					"gb2312");
			LineNumberReader reader = new LineNumberReader(streamReader);
			String line = null;
			StringBuilder allLine = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				allLine.append(line);
				allLine.append("\n");
			}
			streamReader.close();
			reader.close();
			input.close();
			return allLine.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public int convertDipOrPx(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	public int convertPxOrDip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * @param pxValue
	 * @param fontScale
	 * @return
	 */
	public int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * @param spValue
	 * @param fontScale
	 * @return
	 */
	public int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public String dealString(String st, int size) {
		int value = size;
		if (st.length() >= value)
			return "  " + st + "  ";
		else {
			int t = (value - st.length()) / 2;
			for (int i = 0; i < t; i++) {
				st = " " + st + "  ";
			}
			return st;
		}
	}

	public String getTimeByFormat(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		return str;
	}

	public String getDateTimeBylong(long time_data, String dateformat_batt) {
		Date date = new Date(time_data);
		SimpleDateFormat format = new SimpleDateFormat(dateformat_batt);
		return format.format(date);
	}

	public String getNameByFlag(String source, String flag) {
		String s = source.toLowerCase().replace(flag, "");
		return s.trim();
	}

	/**
	 * @param paramContext
	 * @param paramString
	 * @return
	 * @throws IOException
	 */
	public InputStream getAssetsInputStream(Context paramContext,
                                            String paramString) throws IOException {
		return paramContext.getResources().getAssets().open(paramString);
	}
	
		public Bitmap getBitmap(InputStream is, int sample){
			   BitmapFactory.Options opt = new BitmapFactory.Options();
		        opt.inPreferredConfig = Bitmap.Config.RGB_565;
		       opt.inPurgeable = true;   
		       opt.inInputShareable = true; 
		       opt.inSampleSize = sample;
		           return BitmapFactory.decodeStream(is,null,opt);
		}
		
		
		
		
		public static Bitmap getBitmap(String path) {

			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			return new BitmapFactory().decodeFile(path, opt);
		}
		
		public static Bitmap getBitmap(Context mContext, Uri uri) throws FileNotFoundException {
			ContentResolver cr = mContext.getContentResolver();
			InputStream in = cr.openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			return bitmap;
		}

}
