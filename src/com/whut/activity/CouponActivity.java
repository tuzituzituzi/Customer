package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.whut.activity.Fragment.CouponUnuseFragment;
import com.whut.activity.Fragment.CouponUsedFragment;
import com.whut.customer.R;
import com.whut.customer.R.color;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;
import com.whut.data.model.CouponModel;

public class CouponActivity extends Activity implements OnClickListener {

	private FragmentManager fm;
	private CouponUsedFragment usedfrag;
	private CouponUnuseFragment unusefrag;

	private List<CouponModel> unuse_list;
	private List<CouponModel> used_list;

	private TextView used_tv;
	private TextView unuse_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);
		initView();
		fm = getFragmentManager();
		setTabView(0);
	}

	private void setTabView(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			unuse_tv.setTextColor(getResources().getColor(R.color.white));
			unuse_tv.setBackgroundColor(getResources().getColor(R.color.coupon_bg));
			used_tv.setTextColor(getResources().getColor(R.color.coupon_bg));
			used_tv.setBackgroundColor(getResources().getColor(R.color.white));
			if (unusefrag != null) {
				transaction.show(unusefrag);
			} else {
				unusefrag = new CouponUnuseFragment();
				Bundle data = new Bundle();
				data.putSerializable("data",
						getIntent().getSerializableExtra("unusecoupon"));
				unusefrag.setArguments(data);
				transaction.add(R.id.coupon_content, unusefrag);
			}
			break;
		case 1:
			used_tv.setTextColor(getResources().getColor(R.color.white));
			used_tv.setBackgroundColor(getResources().getColor(R.color.coupon_bg));
			unuse_tv.setTextColor(getResources().getColor(R.color.coupon_bg));
			unuse_tv.setBackgroundColor(getResources().getColor(R.color.white));
			if (usedfrag != null) {
				transaction.show(usedfrag);
			} else {
				usedfrag = new CouponUsedFragment();
				Bundle data = new Bundle();
				data.putSerializable("data",
						getIntent().getSerializableExtra("usedcoupon"));
				usedfrag.setArguments(data);
				transaction.add(R.id.coupon_content, usedfrag);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (usedfrag != null)
			transaction.hide(usedfrag);
		if (unusefrag != null)
			transaction.hide(unusefrag);
	}

	private void initView() {
		// TODO Auto-generated method stub
		unuse_tv = (TextView) findViewById(R.id.unuse_coupon);
		used_tv = (TextView) findViewById(R.id.used_coupon);

		unuse_tv.setOnClickListener(this);
		used_tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.unuse_coupon:
			setTabView(0);
			break;
		case R.id.used_coupon:
			setTabView(1);
			break;
		case R.id.coupon_back:
			finish();
			break;

		}

	}
}
