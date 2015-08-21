package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import com.whut.customer.R;
import com.whut.util.WifiAdmin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FreeWifiActivity extends Activity implements OnClickListener{
	private ListView mWifilsit;
	//ToggleButton mTurnwifi;
	wifiAdapeter adapter;
	private WifiBroadRecever mWifiBroadRecever;
	public static final int TYPE_NO_PASSWD = 0x11;
	public static final int TYPE_WEP = 0x12;
	public static final int TYPE_WPA = 0x13;
	private String mLocalShow = "已连接";
	private TextView mConnectshow;
	private String TAG = "test";
	private PopupWindow mShowCPopu = null;
	private View mShowCView;
	private WifiAdmin wifiAdmin;
	public List<ScanResult> wifiList;
	public WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_freewifi);

		wifiAdmin = new WifiAdmin(FreeWifiActivity.this);
//		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		wifiManager.startScan();
//		wifiList = wifiManager.getScanResults();
//		Log.i(TAG, "wifiList.size() = "+ wifiList.size());
		
		initView();
		RegisterWifiRecever();
		refreshWifiList();
	}

	private void pXspy() {
		wifiAdmin.getWifiList();
//		wifiAdmin.mWifiList = wifiList;
		if (wifiAdmin.mWifiList != null) {
			System.out.println("mWifiList.size()"+ wifiAdmin.mWifiList.size());
			adapter = new wifiAdapeter();
			mWifilsit.setAdapter(adapter);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		unregisterReceiver(mWifiBroadRecever);
	}

	private void pXspys() {
		wifiAdmin.getWifiList();
		if (wifiAdmin.mWifiList != null) {
			adapter.notifyDataSetChanged();
		}
		if (wifiAdmin.mWifiList == null) {
			wifiAdmin.mWifiList = new ArrayList<ScanResult>();
			adapter.notifyDataSetChanged();
		}
	}

	// /
	/**
	 * 初始化界面
	 */
	private void initView() {
		// TODO Auto-generated method stub
		mWifilsit = (ListView) findViewById(R.id.wifishowlist);
		mConnectshow = (TextView) findViewById(R.id.showconnect);
		
		if(wifiAdmin.mWifiManager.isWifiEnabled()){
			
			pXspy();
			
		}
		else{
			System.out.println("打开wifi");
			wifiAdmin.openWifi();
			pXspy();
			
		}
		

		mWifilsit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int loac = arg2 - wifiAdmin.mWifiList.size();
//				Log.i(TAG, "local position:" + arg2 + "long:" + arg3);
				System.out.println( "local position:" + arg2 + "  long:" + arg3);
				if (loac < 0) {
					Log.i(TAG, "local position:" + arg2);
					ScanResult sr = wifiAdmin.mWifiList.get(arg2);

					Log.i(TAG, "<----->" + sr.SSID);
					WifiConfiguration c = wifiAdmin.checkWiFiConfig(sr.SSID);
					if (c != null) {
						wifiAdmin.mWifiManager.enableNetwork(c.networkId, true);

					} else {
						// 如果没有输入密码 且配置列表中没有该WIFI
						/* WIFICIPHER_WPA 加密 */

						if (sr.capabilities.contains("WPA-PSK")) {
							// Log.i(TAG, "config----WPA-PSK");
							// int netid =
							// mWifiManager.addNetwork(createWifiInfo(
							// sr.SSID, "87654321", 3));
							System.out.println("config----WPA-PSK");
							showLoadingPop(sr.SSID);
							// mWifiManager.enableNetwork(netid, true);
						} else if (sr.capabilities.contains("WEP")) {
							/* WIFICIPHER_WEP 加密 */
//							Log.i(TAG, "config----WEP");
							System.out.println("config----WEP");
							int netid = wifiAdmin.mWifiManager.addNetwork(wifiAdmin.createWifiInfo(
									sr.SSID, "87654321", 2));
							wifiAdmin.mWifiManager.enableNetwork(netid, true);
						} else {
							/* WIFICIPHER_OPEN NOPASSWORD 开放无加密 */
							int netid = wifiAdmin.mWifiManager.addNetwork(wifiAdmin.createWifiInfo(
									sr.SSID, "", 1));
							wifiAdmin.mWifiManager.enableNetwork(netid, true);
						}
					}
				}
			}
		});

	}

	private void showLoadingPop(final String ssid) {
		Button btn_ok, btn_cancel;
		TextView title;
		final EditText password;
		if (mShowCView == null) {
			mShowCView = this.getLayoutInflater().inflate(R.layout.freewifi_pop, null);
			//mShowCView.setBackgroundResource(R.drawable.tv_show);
		}
		if (mShowCPopu == null) {
			mShowCPopu = new PopupWindow(mShowCView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);

		}
		title = (TextView) mShowCView.findViewById(R.id.pop_title);
		password = (EditText) mShowCView.findViewById(R.id.pop_password);
		btn_ok = (Button) mShowCView.findViewById(R.id.pop_bt_ok);
		btn_cancel = (Button) mShowCView.findViewById(R.id.pop_bt_cancel);
		mShowCPopu.setFocusable(true);
		mShowCPopu.setOutsideTouchable(true);
		mShowCPopu.setBackgroundDrawable(new BitmapDrawable());
		title.setText("" + ssid);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String passwords = password.getText().toString().trim();
				if (passwords != null && !passwords.equals("")) {
					int netid = wifiAdmin.mWifiManager.addNetwork(wifiAdmin.createWifiInfo(ssid,
							passwords, 3));
					wifiAdmin.mWifiManager.enableNetwork(netid, true);
					mShowCPopu.dismiss();
					int i = isWifiContected(getApplicationContext());
//					Log.i("test", "locat :" + i);
					System.out.println("locat :" + i);
				}

			}
		});
//		btn_cancel.setOnClickListener();
//		btn_cancel.setOnClickListener(listener);
//		btn_ok.setOnClickListener(listener);
		mShowCPopu.showAtLocation(mShowCView, Gravity.CENTER, 0, 0);
	}



	private void RegisterWifiRecever() {
		mWifiBroadRecever = new WifiBroadRecever();
		IntentFilter fileter = new IntentFilter();
		fileter.addAction(WifiManager.ACTION_PICK_WIFI_NETWORK);
		fileter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		fileter.addAction(WifiManager.RSSI_CHANGED_ACTION);
		registerReceiver(this.mWifiBroadRecever, fileter);

	}

	private void refreshWifiList() {
		handler.removeMessages(10);
		handler.sendEmptyMessageDelayed(10, 5000);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 10) {
				pXspy();
			}
		}

	};

	class wifiAdapeter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			if (wifiAdmin.mWifiConfiguration != null && wifiAdmin.mWifiList != null) {
//				return wifiAdmin.mWifiList.size() + wifiAdmin.mWifiConfiguration.size();
//			}
			if (wifiAdmin.mWifiList != null)
				return wifiAdmin.mWifiList.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			int local = position - wifiAdmin.mWifiList.size();
			View v = getLayoutInflater().inflate(R.layout.freewifi_item, null);
			ImageView img = (ImageView) v.findViewById(R.id.img);
			ImageView img1= (ImageView) v.findViewById(R.id.img1);
			TextView tx = (TextView) v.findViewById(R.id.title);
			TextView tx1 = (TextView) v.findViewById(R.id.isconnect);
//			Button btn_isconnect = (Button) findViewById(R.id.isconnect);
			//img.setImageResource(R.drawable.wifi0401);
			String currSsid = wifiAdmin.mWifiInfo.getSSID();
			if (local < 0) {
				ScanResult sr = wifiAdmin.mWifiList.get(position);
				String ssid = sr.SSID;
				System.out.println("sr.SSID"+ssid);
				System.out.println("currSsid "+currSsid);
				tx.setText(ssid);
				if(wifiAdmin.ifConfigured(ssid,wifiAdmin.mWifiManager)){
					tx1.setText("已保存，通过WPA/WPA2进行保护");
				}else if(sr.capabilities.contains("WPA-PSK")){
					tx1.setText("通过WPA/WPA2进行保护");
				}else if (sr.capabilities.contains("WEP")) {
					tx1.setText("通过WEP进行保护");
				} else {
					tx1.setText("");
				}
				
				if (currSsid != null)
					if (currSsid.equals("\""+ssid+"\"")) {
						System.out.println("eques");
						tx1.setText("已连接");
						img1.setImageDrawable(getResources().getDrawable(R.drawable.wifi_connected));
					}
				
				System.out.println(sr.SSID +"  Math.abs(sr.level) " + Math.abs(sr.level));
				Log.i("text", sr.SSID +"  Math.abs(sr.level) " + Math.abs(sr.level));
				if (Math.abs(sr.level) > 100) {
					img.setImageDrawable(getResources().getDrawable(
							R.drawable.wifi_1));
				} else if (Math.abs(sr.level) > 85) {
					img.setImageDrawable(getResources().getDrawable(
							R.drawable.wifi_2));
				} else if (Math.abs(sr.level) > 75) {
					img.setImageDrawable(getResources().getDrawable(
							R.drawable.wifi_3));
				} else if (Math.abs(sr.level) > 65) {
					img.setImageDrawable(getResources().getDrawable(
							R.drawable.wifi_3));
				} else {
					img.setImageDrawable(getResources().getDrawable(
							R.drawable.wifi_4));
				}
				


			}
			return v;
		}
	}

	public static final int WIFI_CONNECTED = 0x01;
	public static final int WIFI_CONNECT_FAILED = 0x02;
	public static final int WIFI_CONNECTING = 0x03;

	public int isWifiContected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		System.out.println("isConnectedOrConnecting = "
				+ wifiNetworkInfo.isConnectedOrConnecting());
		System.out.println("wifiNetworkInfo.getDetailedState() = "
				+ wifiNetworkInfo.getDetailedState());
		if (wifiNetworkInfo.getDetailedState() == DetailedState.OBTAINING_IPADDR
				|| wifiNetworkInfo.getDetailedState() == DetailedState.CONNECTING) {
			return WIFI_CONNECTING;
		} else if (wifiNetworkInfo.getDetailedState() == DetailedState.CONNECTED) {
			return WIFI_CONNECTED;
		} else {
			System.out.println("getDetailedState() == "
					+ wifiNetworkInfo.getDetailedState());
			return WIFI_CONNECT_FAILED;
		}
	}



	class WifiBroadRecever extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("action info:"+intent.getAction());
			if (intent.getAction().equals(WifiManager.ACTION_PICK_WIFI_NETWORK)) {

			} else if (intent.getAction().equals(
					WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

				// pXspy();
			}

			//打开wifi木有连接时，是RSSI_CJANGED
			if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
				Log.d(TAG, "RSSI changed");
				System.out.println("RSSI changed");
				// 有可能是正在获取，或者已经获取了
				Log.d(TAG, " intent is " + WifiManager.RSSI_CHANGED_ACTION);
				System.out.println(" intent is " + WifiManager.RSSI_CHANGED_ACTION);
				
				if (isWifiContected(getApplicationContext()) == WIFI_CONNECTED) {
					// stopTimer();
					// onNotifyWifiConnected();
					// unRegister();
					wifiAdmin.mWifiInfo = wifiAdmin.mWifiManager.getConnectionInfo();
					mLocalShow = "已连接";
					mConnectshow
							.setText(wifiAdmin.mWifiInfo.getSSID() + ":" + mLocalShow);
					pXspy();
				} else if (isWifiContected(getApplicationContext()) == WIFI_CONNECT_FAILED) {
					// stopTimer();
					System.out.println("关闭wifi");
					//closeWifi();
					// onNotifyWifiConnectFailed();
					// unRegister();
				} else if (isWifiContected(getApplicationContext()) == WIFI_CONNECTING) {
					mLocalShow = "连接中";
					mConnectshow.setText(mLocalShow);
				}
			}
		}

	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.freewifi_back:
			finish();
			break;

		}
	}

}
