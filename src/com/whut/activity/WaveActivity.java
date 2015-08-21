package com.whut.activity;

import java.io.IOException;
import java.util.HashMap;

import com.whut.customer.R;
import com.whut.customer.R.layout;
import com.whut.service.WaveService;
import com.whut.util.ShakeListener;
import com.whut.util.ShakeListener.OnShakeListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WaveActivity extends Activity {
	private final int DURATION_TIME = 600;

	private ShakeListener mShakeListener = null;

	private Vibrator mVibrator;

	private RelativeLayout mImgUp;

	private RelativeLayout mImgDn;

	private SoundPool sndPool;

	private HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wave);

		stopService(new Intent(this, WaveService.class));
		WaveService.getListener().stop();
		initView();

		mVibrator = (Vibrator) getApplication().getSystemService(

		VIBRATOR_SERVICE);

		// 检查设备是否有震动装置

		// mVibrator.hasVibrator();

		loadSound();

		mShakeListener = new ShakeListener(this);

		// 监听到手机摇动

		mShakeListener.setOnShakeListener(new OnShakeListener() {

			public void onShake() {

				startAnim();

			}

		});
		startAnim();
	}

	/****
	 * 
	 * 初始化控件
	 */

	private void initView() {

		// TODO Auto-generated method stub

		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);

		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);

	}

	/****
	 * 
	 * 获取音效
	 */

	private void loadSound() {

		sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);

		new Thread() {

			public void run() {

				try {

					soundPoolMap.put(

					0,

					sndPool.load(

					getAssets().openFd(

					"sound/wave_sound.mp3"), 1));

					soundPoolMap.put(1, sndPool.load(

					getAssets().openFd("sound/wave_sound.mp3"), 1));

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}.start();

	}

	public void startAnim() {

		AnimationSet animup = new AnimationSet(true);

		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

		-0.5f);

		mytranslateanimup0.setDuration(DURATION_TIME);

		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

		+0.5f);

		mytranslateanimup1.setDuration(DURATION_TIME);

		mytranslateanimup1.setStartOffset(DURATION_TIME);

		animup.addAnimation(mytranslateanimup0);

		animup.addAnimation(mytranslateanimup1);

		mImgUp.startAnimation(animup);

		AnimationSet animdn = new AnimationSet(true);

		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

		+0.5f);

		mytranslateanimdn0.setDuration(DURATION_TIME);

		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

		Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

		-0.5f);

		mytranslateanimdn1.setDuration(DURATION_TIME);

		mytranslateanimdn1.setStartOffset(DURATION_TIME);

		animdn.addAnimation(mytranslateanimdn0);

		animdn.addAnimation(mytranslateanimdn1);

		mImgDn.startAnimation(animdn);

		// 动画监听，开始时显示加载状态，

		mytranslateanimdn0.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

				mShakeListener.stop();

				sndPool.play(soundPoolMap.get(0), (float) 0.2, (float) 0.2, 0,

				0, (float) 0.6);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				Toast.makeText(getBaseContext(), "摇一摇结束", Toast.LENGTH_SHORT)

				.show();
				mShakeListener.start();

			}

		});

	}

	@Override
	protected void onDestroy() {

		// TODO Auto-generated method stub
		WaveService.getListener().start();
		super.onDestroy();

		if (mShakeListener != null) {

			mShakeListener.stop();

		}

	}

}
