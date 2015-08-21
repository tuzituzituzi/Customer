package com.whut.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetImgByURL {
	// 获取指定路径的图片
	public static Bitmap getImage(String urlpath) throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		Bitmap bitmap = null;
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		return bitmap;
	}
	public static Bitmap returnBitMap(String url){  
        URL myFileUrl = null;    
        Bitmap bitmap = null;   
        try {    
            myFileUrl = new URL(url);    
        } catch (MalformedURLException e) {    
            e.printStackTrace();    
        }    
        try {    
            HttpURLConnection conn = (HttpURLConnection) myFileUrl    
              .openConnection();    
            conn.setDoInput(true);    
            conn.connect();    
            InputStream is = conn.getInputStream();    
            bitmap = BitmapFactory.decodeStream(is);    
            is.close();    
        } catch (IOException e) {    
              e.printStackTrace();    
        }    
              return bitmap;    
    }    

}
