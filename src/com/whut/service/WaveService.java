package com.whut.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View.OnKeyListener;

import com.whut.activity.WaveActivity;
import com.whut.util.ShakeListener;
import com.whut.util.ShakeListener.OnShakeListener;

public class WaveService extends Service {
	private IBinder binder;
	private static ShakeListener listener;
	private static boolean flag = true;

	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		listener = new ShakeListener(getApplicationContext());
		listener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						WaveActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				if (flag)
					startActivity(intent);
			}
		});
		
		return START_NOT_STICKY;
	}

	public static boolean isFlag() {
		return flag;
	}

	public static void setFlag(boolean f) {
		flag = f;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public static ShakeListener getListener() {
		return listener;
	}

	public void setListener(ShakeListener listener) {
		this.listener = listener;
	}
}
