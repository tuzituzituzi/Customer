package com.whut.util;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
	// 定义WifiManager对象
		public WifiManager mWifiManager;
		// 定义WifiInfo对象
		public WifiInfo mWifiInfo;
		// 扫描出的网络连接列表
		public List<ScanResult> mWifiList;
		// 网络连接列表
		public List<WifiConfiguration> mWifiConfiguration;
		// 定义一个WifiLock
		WifiLock mWifiLock;
		
		public WifiAdmin(Context context){
			mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			// 取得WifiInfo对象
			System.out.println("mWifiManager="+mWifiManager);
			mWifiInfo = mWifiManager.getConnectionInfo();
			System.out.println("mWifiInfo=" + mWifiInfo);
		}
		
		public WifiConfiguration checkWiFiConfig(String str) {
			System.out.println("str " + str);
			for (WifiConfiguration C : mWifiConfiguration) {
				System.out.println("C.SSID " + C.SSID);
				if (C.SSID.equals("\"" +str+ "\"")) {
					return C;
				}
			}
			return null;
		}
		
		public void getWifiList() {
			if(mWifiManager.startScan() == true)
				System.out.println("扫描成功");
			// 得到扫描结果
			mWifiList = mWifiManager.getScanResults();
			System.out.println("mWifiList.size()="+mWifiList.size());
			if(mWifiList == null){
				System.out.println("mWifiList is null");
			}else{
				System.out.println("mWifiList="+ mWifiList);
			}
			// 得到配置好的网络连接
			mWifiConfiguration = mWifiManager.getConfiguredNetworks();
		}
		
		// 打开WIFI
		public void openWifi() {
			if (!mWifiManager.isWifiEnabled()) {
				mWifiManager.setWifiEnabled(true);
			}
		}

		// 得到WifiInfo的所有信息包
		public String getWifiInfo() {
			return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
		}

		// 关闭WIFI
		public void closeWifi() {
			if (mWifiManager.isWifiEnabled()) {
				mWifiManager.setWifiEnabled(false);
			}
		}

		// 指定配置好的网络进行连接
		public void connectConfiguration(int index) {
			// 索引大于配置好的网络索引返回
			if (index > mWifiConfiguration.size()) {
				return;
			}
			// 连接配置好的指定ID的网络
			mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
					true);
		}

		// 检查当前WIFI状态
		public int checkState() {
			return mWifiManager.getWifiState();
		}
		
		public WifiConfiguration IsExsits(String SSID) {
			List<WifiConfiguration> existingConfigs = mWifiManager
					.getConfiguredNetworks();
			for (WifiConfiguration existingConfig : existingConfigs) {
				if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
					return existingConfig;
				}
			}
			return null;
		}
		
		public WifiConfiguration createWifiInfo(String SSID, String Password,
				int Type) {
			//Log.v(TAG, "SSID = " + SSID + "## Password = " + Password + "## Type = " + Type);
			System.out.println("SSID = " + SSID + "## Password = " + Password + "## Type = " + Type);
			if (SSID == null || Password == null || SSID.equals("")) {
				//Log.e(TAG, "addNetwork() ## nullpointer error!");
				System.out.println("addNetwork() ## nullpointer error!");
				return null;
			}
			WifiConfiguration config = new WifiConfiguration();
			config.allowedAuthAlgorithms.clear();
			config.allowedGroupCiphers.clear();
			config.allowedKeyManagement.clear();
			config.allowedPairwiseCiphers.clear();
			config.allowedProtocols.clear();
			config.SSID = "\"" + SSID + "\"";

			WifiConfiguration tempConfig = isExsits(SSID, mWifiManager);
			if (tempConfig != null) {
				mWifiManager.removeNetwork(tempConfig.networkId);
			}

			if (Type == 1) // WIFICIPHER_NOPASS
			{
				config.wepKeys[0] = "";
				config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
				config.wepTxKeyIndex = 0;
			}
			if (Type == 2) // WIFICIPHER_WEP
			{
				config.hiddenSSID = true;
				config.wepKeys[0] = "\"" + Password + "\"";
				config.allowedAuthAlgorithms
						.set(WifiConfiguration.AuthAlgorithm.SHARED);
				config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
				config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
				config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
				config.allowedGroupCiphers
						.set(WifiConfiguration.GroupCipher.WEP104);
				config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
				config.wepTxKeyIndex = 0;
			}
			if (Type == 3) // WIFICIPHER_WPA
			{
				config.preSharedKey = "\"" + Password + "\"";
				config.hiddenSSID = true;
				config.allowedAuthAlgorithms
						.set(WifiConfiguration.AuthAlgorithm.OPEN);
				config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
				config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
				config.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.TKIP);
				// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
				config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
				config.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.CCMP);
				config.status = WifiConfiguration.Status.ENABLED;
			}
			return config;
		}

		/**
		 * 判断wifi是否存在
		 * 
		 * @param SSID
		 * @param wifiManager
		 * @return
		 */
		public static WifiConfiguration isExsits(String SSID,
				WifiManager wifiManager) {
			List<WifiConfiguration> existingConfigs = wifiManager
					.getConfiguredNetworks();
			for (WifiConfiguration existingConfig : existingConfigs) {
				if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
					return existingConfig;
				}
			}
			return null;
		}

		public boolean ifConfigured(String ssid, WifiManager mWifiManager2) {
			// TODO Auto-generated method stub
			if(isExsits(ssid, mWifiManager2) != null)
				return true;
			return false;
		}

}
