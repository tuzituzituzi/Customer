package com.whut.util;


import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	public static boolean isGoodJson(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		try {
			JSONObject.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
