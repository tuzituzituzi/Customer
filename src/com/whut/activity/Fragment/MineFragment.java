package com.whut.activity.Fragment;

import java.io.File;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.whut.activity.AboutActivity;
import com.whut.activity.CouponActivity;
import com.whut.activity.LoginActivity;
import com.whut.activity.WaveActivity;
import com.whut.activity.map.ShowMap;
import com.whut.customer.R;
import com.whut.util.DataCleanManager;
import com.whut.util.UpdateManager;

public class MineFragment extends Fragment implements OnClickListener {

	private Intent tempIntent;
	private Context context;

	private RelativeLayout login_layout;
	private RelativeLayout wave_layout;
	private RelativeLayout myorder_layout;
	private RelativeLayout collection_layout;
	private RelativeLayout on_sale_layout;
	private RelativeLayout update_layout;
	private RelativeLayout clear_cache_layout;
	private RelativeLayout local_layout;
	private RelativeLayout feedback_layout;
	private RelativeLayout advice_layout;
	private RelativeLayout about_layout;

	private ImageView local_switch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_mine, container, false);
		context = getActivity();
		initView(view);
		return view;
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		login_layout = (RelativeLayout) v.findViewById(R.id.login_layout);
		wave_layout = (RelativeLayout) v.findViewById(R.id.wave_layout);
		myorder_layout = (RelativeLayout) v.findViewById(R.id.myorder_layout);
		collection_layout = (RelativeLayout) v
				.findViewById(R.id.collection_layout);
		on_sale_layout = (RelativeLayout) v
				.findViewById(R.id.mine_coupon_layout);
		update_layout = (RelativeLayout) v.findViewById(R.id.update_layout);
		clear_cache_layout = (RelativeLayout) v
				.findViewById(R.id.clear_cache_layout);
		local_layout = (RelativeLayout) v.findViewById(R.id.local_layout);
		feedback_layout = (RelativeLayout) v.findViewById(R.id.feedback_layout);
		advice_layout = (RelativeLayout) v.findViewById(R.id.advice_layout);
		about_layout = (RelativeLayout) v.findViewById(R.id.about_layout);

		local_switch = (ImageView) v.findViewById(R.id.local_switch);

		login_layout.setOnClickListener(this);
		wave_layout.setOnClickListener(this);
		myorder_layout.setOnClickListener(this);
		collection_layout.setOnClickListener(this);
		on_sale_layout.setOnClickListener(this);
		update_layout.setOnClickListener(this);
		clear_cache_layout.setOnClickListener(this);
		local_layout.setOnClickListener(this);
		feedback_layout.setOnClickListener(this);
		advice_layout.setOnClickListener(this);
		about_layout.setOnClickListener(this);
		local_switch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.local_switch:
			switchchange(v);
			break;
		case R.id.login_layout:
			tempIntent = new Intent(context,LoginActivity.class);
			startActivity(tempIntent);
			break;
		case R.id.wave_layout:
			tempIntent = new Intent(context, WaveActivity.class);
			startActivity(tempIntent);
			break;
		case R.id.myorder_layout:
			break;
		case R.id.collection_layout:
			break;
		case R.id.mine_coupon_layout:
			// startActivity(new Intent(context, CouponActivity.class));
			break;
		case R.id.update_layout:
			UpdateManager mUpdateManager = new UpdateManager(context);
			mUpdateManager.checkUpdateInfo();
			break;
		case R.id.clear_cache_layout:
			clearCache();
			break;
		case R.id.local_layout:
			new ShowMap(context);
			break;
		case R.id.feedback_layout:
			break;
		case R.id.advice_layout:
			break;
		case R.id.about_layout:
			tempIntent = new Intent(context, AboutActivity.class);
			startActivity(tempIntent);
			break;

		default:
			break;
		}
	}

	//http://blog.csdn.net/wwj_748/article/details/42737607
	private void clearCache() {
		File file = new File(context.getExternalCacheDir().getPath());
		System.out.println("file.toString() "+ file.toString());
		String cacheSize;
		try {
			cacheSize = DataCleanManager.getCacheSize(file);
			DataCleanManager.cleanExternalCache(context);
			Toast.makeText(context, "已清除缓存" + cacheSize,
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception");
			e.printStackTrace();
		}
		
	}

	private void switchchange(View v) {
		ImageView iv = (ImageView) v;
		if ("0".equals(iv.getTag())) {
			iv.setImageResource(R.drawable.ios7_switch_on);
			iv.setTag("1");
		} else if ("1".equals(iv.getTag())) {
			iv.setImageResource(R.drawable.ios7_switch_off);
			iv.setTag("0");
		}
	}
}
