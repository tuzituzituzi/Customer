package com.whut.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjie.imageloadersample.imageloader.ImageLoader;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.data.model.CouponModel;
import com.whut.data.model.GoodsModel;
import com.whut.util.DownloadURLFile;
import com.whut.util.JsonUtils;
import com.whut.util.MyImgScroll;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;
import com.whut.util.PullToRefreshListView;
import com.whut.util.WebHelper;

public class PlaceActivity extends Activity implements OnClickListener {
	private Context context;
	private ListView listview;
	private List<GoodsModel> items;
	private PullToRefreshListView pullRefreshListView;
	private List<CouponModel> unuse_list;
	private List<CouponModel> used_list;

	private MainListAdapter listAdapter;
	private LayoutInflater inflater;
	private String path;
	private TextView coupon_num_tv;
	private int coupon_num;

	MyImgScroll myPager; // 图片容器
	LinearLayout ovalLayout; // 圆点容器
	private List<ImageView> listViews; // 图片组
	private int adv_sides;
	private List<String> urls;
	private boolean advFlag;
	private GetAdvImg getAdv;
	private GetGoodsList getGoods;
	private GetCouponList getCoupon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy);
		 */
		initData();
		pullRefreshListView = (PullToRefreshListView) findViewById(R.id.Goods_ListView);
		initRefreshListView();
		listview = pullRefreshListView.getRefreshableView();
		// listview=(ListView) findViewById(R.id.Goods_ListView);
		listAdapter = new MainListAdapter();
		if (listAdapter == null)
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		listview.setAdapter(listAdapter);
		enterGoodsDetail();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		InitViewPager();
		getAdv = new GetAdvImg();
		getAdv.execute();
		getGoods = new GetGoodsList();
		getGoods.execute();
		getCoupon = new GetCouponList();
		getCoupon.execute();
	}

	private void initRefreshListView() {
		// TODO Auto-generated method stub

		pullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 下拉刷新
				Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
				pullRefreshListView.onRefreshComplete();
			}
		});

		pullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						// 上拉刷新
						Toast.makeText(context, "上拉刷新", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void enterGoodsDetail() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < 3)
					return;
				Intent intent = new Intent(context, GoodsDetailActivity.class);
				intent.putExtra("goods", items.get(position - 4));
				startActivity(intent);
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
		items = new ArrayList<GoodsModel>();
		unuse_list = new ArrayList<CouponModel>();
		used_list = new ArrayList<CouponModel>();
		path = context.getCacheDir().getAbsolutePath();
		urls = new ArrayList<String>();
		advFlag = false;
	}

	@Override
	protected void onRestart() {
		// myPager.startTimer();
		super.onRestart();
	}

	@Override
	protected void onStop() {
		myPager.stopTimer();
		super.onStop();
	}

	public void stop(View v) {
		myPager.stopTimer();
	}

	/**
	 * 初始化图片
	 */
	private void InitViewPager() {
		listViews = new ArrayList<ImageView>();
	}

	class GetAdvImg extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = "";
			String url = Constants.GET_ADS_WALL;
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("mall", "0"));
			// http://202.114.175.253:80/ECheckServer/login/load.do?username=zym&password=12345
			try {
				result = WebHelper.getJsonString(url, list);
				Log.i("test", "result="+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (JsonUtils.isGoodJson(result)) {
				JSONObject json = JSONObject.parseObject(result);
				int code = json.getIntValue("code");
				String msg = json.getString("msg");
				if (code == 1) {
					JSONArray items = json.getJSONArray("data");
					adv_sides = items.size();
					for (int i = 0; i < items.size(); i++) {
						JSONObject item = items.getJSONObject(i);
						String img = item.getString("imgUrl");
						urls.add(img);
						final String detail_url = item.getString("detailUrl");
						ImageView imageView = new ImageView(context);
						imageView.setImageResource(R.drawable.ic_launcher);
						imageView.setScaleType(ScaleType.CENTER);
						imageView.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {// 设置图片点击事件
								// 写点击事件
								Intent intent = new Intent(context,
										AdsWebviewActivity.class);
								intent.putExtra("ads_url", detail_url);
								startActivity(intent);
							}
						});
						listViews.add(imageView);
						imageView.setScaleType(ScaleType.FIT_XY);
					}
					listAdapter.notifyDataSetChanged();
					advFlag = true;
				} else {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络ad", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class GetCouponList extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String result = "";
			String url = Constants.GET_COUPON_LIST;
			System.out.println(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("sId", "1"));
			list.add(new BasicNameValuePair("mall", "0"));
			try {
				result = WebHelper.getJsonString(url, list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (JsonUtils.isGoodJson(result)) {
				JSONObject json = JSONObject.parseObject(result);
				int code = json.getIntValue("code");
				String msg = json.getString("msg");
				if (code == 1) {
					JSONArray jsons = json.getJSONArray("data");
					System.out.println(jsons);
					CouponModel coupon;
					for (int i = 0; i < jsons.size(); i++) {
						coupon = new CouponModel();
						JSONObject item = jsons.getJSONObject(i);
						coupon.setCid(item.getString("cId"));
						coupon.setDesc(item.getString("desc"));
						coupon.setThumbnailUrl(item.getString("thumbnailUrl"));
						coupon.setTitle(item.getString("title"));
						int isUsed = item.getIntValue("isUsed");
						if (isUsed == 0)
							unuse_list.add(coupon);
						else
							used_list.add(coupon);
						coupon.setIsUsed(isUsed);
					}
					coupon_num = unuse_list.size();
					coupon_num_tv.setText(String.valueOf(coupon_num));
				} else {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络coupon", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	class GetGoodsList extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = "";
			String url = Constants.GET_GOODS_LIST;
			System.out.println(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("sId", "1"));
			// http://202.114.175.253:80/ECheckServer/login/load.do?username=zym&password=12345
			try {
				result = WebHelper.getJsonString(url, list);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (JsonUtils.isGoodJson(result)) {
				JSONObject json = JSONObject.parseObject(result);
				int code = json.getIntValue("code");
				String msg = json.getString("msg");
				if (code == 1) {
					JSONArray jsons = json.getJSONArray("data");
					System.out.println(jsons);
					GoodsModel goods;
					for (int i = 0; i < jsons.size(); i++) {
						goods = new GoodsModel();
						JSONObject item = jsons.getJSONObject(i);
						String title = item.getString("title");
						String desc = item.getString("desc");
						String img_url = item.getString("thumbnailUrl")
								.replace("\\", "");
						System.out.println(img_url);
						String gid = item.getString("gId");
						int purchase_count = item.getIntValue("purchaseCount");
						String originalPrice = item.getString("originalPrice");
						if (originalPrice == null || "".equals(originalPrice)) {
							originalPrice = "0";
						}
						double origin_price = Double.valueOf(originalPrice);
						String currentPrice = item.getString("currentPrice");
						if (currentPrice == null || "".equals(currentPrice)) {
							currentPrice = "0";
						}
						double current_price = Double.valueOf(currentPrice);

						/*
						 * String filepath = new DownloadURLFile()
						 * .downloadFromUrl(img_url, path);
						 * System.out.println(path + "------" + filepath);
						 */
						goods.setGid(gid);
						goods.setDesc(desc);
						goods.setThumbnailUrl(img_url);
						goods.setOriginPrice(origin_price);
						goods.setCurrentPrice(current_price);
						goods.setPurchaseCount(purchase_count);
						goods.setTitle(title);
						items.add(goods);
						listAdapter.notifyDataSetChanged();
					}
				} else {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络goods", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	class MainListAdapter extends BaseAdapter {
		private class HolderView {
			TextView title;
			TextView desc;
			ImageView img;
			TextView purchase_count;
			TextView origin_price;
			TextView current_price;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size() + 3;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (position == 0) {
				convertView = inflater.inflate(R.layout.welcome_ad, null);
				myPager = (MyImgScroll) convertView.findViewById(R.id.myvp);
				ovalLayout = (LinearLayout) convertView.findViewById(R.id.vb);
				if (advFlag) {
					myPager.start(PlaceActivity.this, listViews, 4000,
							ovalLayout, R.layout.ad_bottom_item,
							R.id.ad_item_v, R.drawable.dot_focused,
							R.drawable.dot_normal);

					for (int i = 0; i < adv_sides; i++) {
						ImageLoader.getInstances().displayImage(urls.get(i),
								listViews.get(i));
					}
				}
				// 初始化图片
				// 开始滚动
				return convertView;
			} else if (position == 1) {
				convertView = inflater.inflate(R.layout.learder_item, null);
				return convertView;
			} else if (position == 2) {
				convertView = inflater
						.inflate(R.layout.place_coupon_item, null);
				coupon_num_tv = (TextView) convertView
						.findViewById(R.id.place_coupon_num);
				coupon_num_tv.setText(String.valueOf(coupon_num));
				return convertView;
			} else {
				HolderView holder;
				if (convertView == null) {
					holder = new HolderView();
					convertView = inflater.inflate(R.layout.goods_listitem,
							null);
					holder.desc = (TextView) convertView
							.findViewById(R.id.goods_list_desc);
					holder.img = (ImageView) convertView
							.findViewById(R.id.goods_list_img);
					holder.origin_price = (TextView) convertView
							.findViewById(R.id.goods_origin_price);
					holder.current_price = (TextView) convertView
							.findViewById(R.id.goods_current_price);
					holder.purchase_count = (TextView) convertView
							.findViewById(R.id.goods_list_purchase_count);
					holder.title = (TextView) convertView
							.findViewById(R.id.goods_list_title);
					holder.origin_price
							.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
					((TextView) convertView
							.findViewById(R.id.goods_origin_price_ex))
							.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
					convertView.setTag(holder);
				} else {
					holder = (HolderView) convertView.getTag();
					if (holder == null) {
						holder = new HolderView();
						convertView = inflater.inflate(R.layout.goods_listitem,
								null);
						holder.desc = (TextView) convertView
								.findViewById(R.id.goods_list_desc);
						holder.img = (ImageView) convertView
								.findViewById(R.id.goods_list_img);
						holder.origin_price = (TextView) convertView
								.findViewById(R.id.goods_origin_price);
						holder.current_price = (TextView) convertView
								.findViewById(R.id.goods_current_price);
						holder.purchase_count = (TextView) convertView
								.findViewById(R.id.goods_list_purchase_count);
						holder.title = (TextView) convertView
								.findViewById(R.id.goods_list_title);
						holder.origin_price
								.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
						((TextView) convertView
								.findViewById(R.id.goods_origin_price_ex))
								.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
						convertView.setTag(holder);
					}
				}
				String desc = items.get(position - 3).getDesc();
				System.out.println("desc--->lenth---->" + desc.length());
				if (desc.length() > 65) {
					desc = desc.substring(0, 60) + "...";
				}
				holder.desc.setText(desc);
				holder.origin_price.setText(String.valueOf(items.get(
						position - 3).getOriginPrice()));
				holder.current_price.setText(String.valueOf(items.get(
						position - 3).getCurrentPrice()));
				holder.purchase_count.setText(String.valueOf(items.get(
						position - 3).getPurchaseCount()));
				holder.title.setText(String.valueOf(items.get(position - 3)
						.getTitle()));
				ImageLoader.getInstances().displayImage(
						items.get(position - 3).getThumbnailUrl(), holder.img,
						100);
				/*
				 * Bitmap bitmap = BitmapFactory.decodeFile(items .get(position
				 * - 3).getImg_url());
				 */
				// holder.img.setImageBitmap(bitmap);
				return convertView;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.place_back_layout:
			finish();
			break;
		case R.id.coupon_item:
			Intent intent = new Intent(context, CouponActivity.class);
			intent.putExtra("unusecoupon", (Serializable) unuse_list);
			intent.putExtra("usedcoupon", (Serializable) used_list);
			startActivity(intent);
			break;
		case R.id.place_freewifi_layout:
			Toast.makeText(context, "place_freewifi_layout", Toast.LENGTH_SHORT)
					.show();
			Intent intent_freewifi = new Intent(context,FreeWifiActivity.class);
			//Intent intent_freewifi = new Intent(context,WifiTestActivity.class);
			startActivity(intent_freewifi);
			break;
		case R.id.place_innerlead_layout:
			Toast.makeText(context, "place_innerlead_layout",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.place_store_layout:
			startActivity(new Intent(context,
					StoreListActivity.class));
			break;
		default:
			break;
		}
	}
}
