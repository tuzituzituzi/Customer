package com.whut.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.whut.activity.Fragment.FindingsFragment;
import com.whut.activity.Fragment.MallFragment;
import com.whut.activity.Fragment.MarketFragment;
import com.whut.activity.Fragment.MineFragment;
import com.whut.broadcast.HomeWatcherReceiver;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.service.WaveService;

public class MainActivity extends Activity implements OnClickListener {

	private MarketFragment market;
	private MallFragment mall;
	private FindingsFragment findings;
	private MineFragment mine;

	private LinearLayout market_layout;
	private LinearLayout mall_layout;
	private LinearLayout findings_layout;
	private LinearLayout mine_layout;
	private FragmentManager fragmentManager;
	private boolean okToExit;
	private static HomeWatcherReceiver mHomeKeyReceiver = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String appId = Constants.GetAPPID; // 蒲公英注册或上传应用获取的AppId
		PgyUpdateManager.register(MainActivity.this, appId,
				new UpdateManagerListener() {
					@Override
					public void onUpdateAvailable(final String result) {
						// 调用sdk的默认下载，apk下载地址为result字符串中downloadURL对应的值
						String downloadUrl = Constants.GetDownLoadURL;
						startDownloadTask(MainActivity.this, downloadUrl);
					}

					@Override
					public void onNoUpdateAvailable() {

					}
				});
		fragmentManager = getFragmentManager();
		initView();
		setTabSelection(0);
		if (WaveService.getListener() == null) {
			startService(new Intent(this, WaveService.class));
		} else {
			WaveService.setFlag(true);
			WaveService.getListener().start();
		}

	}

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub
		resetBtn();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			((ImageView) market_layout.findViewById(R.id.place_img))
					.setImageResource(R.drawable.sel_place);
			((TextView) market_layout.findViewById(R.id.place_tv))
					.setTextColor(getResources().getColor(R.color.orange));
			if (market == null) {
				market = new MarketFragment();
				transaction.add(R.id.id_content, market);
			} else {
				transaction.show(market);
			}
			break;
		case 1:
			((ImageView) mall_layout.findViewById(R.id.mall_img))
					.setImageResource(R.drawable.sel_mall);
			((TextView) mall_layout.findViewById(R.id.mall_tv))
					.setTextColor(getResources().getColor(R.color.orange));
			if (mall == null) {
				mall = new MallFragment();
				transaction.add(R.id.id_content, mall);
			} else {
				transaction.show(mall);
			}
			break;
		case 2:
			((ImageView) findings_layout.findViewById(R.id.findings_img))
					.setImageResource(R.drawable.sel_findings);
			((TextView) findings_layout.findViewById(R.id.findings_tv))
					.setTextColor(getResources().getColor(R.color.orange));
			if (findings == null) {
				findings = new FindingsFragment();
				transaction.add(R.id.id_content, findings);
			} else {
				transaction.show(findings);
			}
			break;
		case 3:
			((ImageView) mine_layout.findViewById(R.id.mine_img))
					.setImageResource(R.drawable.sel_mine);
			((TextView) mine_layout.findViewById(R.id.mine_tv))
					.setTextColor(getResources().getColor(R.color.orange));
			if (mine == null) {
				mine = new MineFragment();
				transaction.add(R.id.id_content, mine);
			} else {
				transaction.show(mine);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (market != null) {
			transaction.hide(market);
		}
		if (mall != null) {
			transaction.hide(mall);
		}
		if (findings != null) {
			transaction.hide(findings);
		}
		if (mine != null) {
			transaction.hide(mine);
		}
	}

	private void resetBtn() {
		// TODO Auto-generated method stub
		((ImageView) market_layout.findViewById(R.id.place_img))
				.setImageResource(R.drawable.unsel_place);
		((ImageView) mall_layout.findViewById(R.id.mall_img))
				.setImageResource(R.drawable.unsel_mall);
		((ImageView) findings_layout.findViewById(R.id.findings_img))
				.setImageResource(R.drawable.unsel_findings);
		((ImageView) mine_layout.findViewById(R.id.mine_img))
				.setImageResource(R.drawable.unsel_mine);
		((TextView) market_layout.findViewById(R.id.place_tv))
				.setTextColor(getResources().getColor(R.color.black));
		((TextView) mall_layout.findViewById(R.id.mall_tv))
				.setTextColor(getResources().getColor(R.color.black));
		((TextView) findings_layout.findViewById(R.id.findings_tv))
				.setTextColor(getResources().getColor(R.color.black));
		((TextView) mine_layout.findViewById(R.id.mine_tv))
				.setTextColor(getResources().getColor(R.color.black));
	}

	private void initView() {
		// TODO Auto-generated method stub
		market_layout = (LinearLayout) findViewById(R.id.market_layout);
		mall_layout = (LinearLayout) findViewById(R.id.mall_layout);
		findings_layout = (LinearLayout) findViewById(R.id.findings_layout);
		mine_layout = (LinearLayout) findViewById(R.id.mine_layout);

		market_layout.setOnClickListener(this);
		mall_layout.setOnClickListener(this);
		findings_layout.setOnClickListener(this);
		mine_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.market_layout:
			setTabSelection(0);
			break;
		case R.id.mall_layout:
			setTabSelection(1);
			break;
		case R.id.findings_layout:
			setTabSelection(2);
			break;
		case R.id.mine_layout:
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// KeyEvent.KEYCODE_BACK代表返回操作.
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 处理返回操作.
			if (okToExit) {
				WaveService.getListener().stop();
				WaveService.setFlag(false);
				this.finish();
			} else {
				okToExit = true;
				Toast.makeText(this, "再按一次退出-二维码", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						okToExit = false;
					}
				}, 2000); // 2秒后重置
			}
		}
		return true;
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("------------------->onReStart");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		System.out.println("------------------->onStop");
		super.onStop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerHomeKeyReceiver(this);
		if (WaveService.getListener() != null) {
			WaveService.getListener().start();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterHomeKeyReceiver(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("------------------->onDestroy");
		super.onDestroy();
	}

	private static void registerHomeKeyReceiver(Context context) {
		mHomeKeyReceiver = new HomeWatcherReceiver();
		final IntentFilter homeFilter = new IntentFilter(
				Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

		context.registerReceiver(mHomeKeyReceiver, homeFilter);
	}

	private static void unregisterHomeKeyReceiver(Context context) {
		if (null != mHomeKeyReceiver) {
			context.unregisterReceiver(mHomeKeyReceiver);
		}
	}
}

/*
 * 地图初始化 initMap(); tempIntent=new Intent(tabInstance, LocationActivity.class);
 * startActivity(tempIntent); 二维码初始化 tempIntent=new Intent(tabInstance,
 * CaptureActivity.class); startActivity(tempIntent); break;
 */
