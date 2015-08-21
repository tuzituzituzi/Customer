package com.whut.activity;

import com.whut.customer.R;
import com.whut.util.WifiAdmin;

import android.app.Activity;
import android.os.Bundle;

public class WifiTestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		WifiAdmin wifi = new WifiAdmin(WifiTestActivity.this);
		wifi.getWifiList();
		System.out.println("wifi.mWifiList = "+wifi.mWifiList);
		
	}
	

}
