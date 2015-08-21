package com.whut.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DownloadURLFile {
	/**
	 * * @param args
	 */
	public String downloadFromUrl(String url, String dir) {
		String fileName = "";
		fileName = getFileNameFromUrl(url);
		System.out.println(fileName);
		new DownLoadIMG().execute(url, dir, fileName);
		return dir + "/" + fileName;
	}

	public static String getFileNameFromUrl(String url) {
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if (index > 0) {
			name = url.substring(index + 1);
			if (name.trim().length() > 0) {
				return name;
			}
		}
		return name;
	}

	class DownLoadIMG extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				URL httpurl = new URL(params[0]);
				String dir = params[1];
				String fileName = params[2];
				File f = new File(dir + "/" + fileName);
				FileUtils.copyURLToFile(httpurl, f);
				return dir + "/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		}

	}
}
