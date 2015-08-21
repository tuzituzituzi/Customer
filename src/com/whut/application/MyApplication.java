package com.whut.application;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;

import com.pgyersdk.crash.PgyCrashManager;
import com.wangjie.androidbucket.thread.ThreadPool;
import com.wangjie.imageloadersample.imageloader.CacheConfig;
import com.wangjie.imageloadersample.imageloader.ImageLoader;
import com.whut.activity.WaveActivity;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.service.WaveService;
import com.whut.util.ShakeListener;
import com.whut.util.ShakeListener.OnShakeListener;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoader.init(getApplicationContext(), new CacheConfig()
				.setDefRequiredSize(600) // 设置默认的加载图片尺寸（表示宽高任一不超过该值，默认是70px）
				.setDefaultResId(R.drawable.ic_launcher) // 设置显示的默认图片（默认是0，即空白图片）
				.setBitmapConfig(Bitmap.Config.ARGB_8888) // 设置图片位图模式（默认是Bitmap.CacheConfig.ARGB_8888）
				.setMemoryCachelimit(Runtime.getRuntime().maxMemory() / 3) // 设置图片内存缓存大小（默认是Runtime.getRuntime().maxMemory()
																			// /
																			// 4）
				// .setFileCachePath(Environment.getExternalStorageDirectory().toString()
				// + "/mycache") // 设置文件缓存保存目录
				);
		ThreadPool.initThreadPool(10);
		String appId = Constants.GetAPPID; // 蒲公英注册或上传应用获取的AppId
		PgyCrashManager.register(this, appId);
		
		

	}

}
