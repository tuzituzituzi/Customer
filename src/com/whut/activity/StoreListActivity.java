package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjie.imageloadersample.imageloader.ImageLoader;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;
import com.whut.data.model.GoodsModel;
import com.whut.data.model.ShopModel;
import com.whut.util.JsonUtils;
import com.whut.util.PullToRefreshListView;
import com.whut.util.WebHelper;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;

public class StoreListActivity extends Activity implements OnClickListener {

	private List<ShopModel> list;
	private ListView listview;
	private PullToRefreshListView refreshListview;
	private LayoutInflater inflater;
	private StoreListAdapter adapter;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_list);
		initData();
		initView();
		enterShopDetail();
	}

	private void enterShopDetail() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ShopDetailActivity.class);
				// intent.putExtra("goods", list.get(position - 4));
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		context = this;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		listview =(ListView) findViewById(R.id.Store_ListView);
		refreshListview = (PullToRefreshListView) findViewById(R.id.Store_ListView);
		initRefreshListView();
		listview = refreshListview.getRefreshableView();
		adapter = new StoreListAdapter();
		listview.setAdapter(adapter);
	}

	private void initRefreshListView() {
		// TODO Auto-generated method stub

		refreshListview.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 下拉刷新
				Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
				refreshListview.onRefreshComplete();
			}
		});

		refreshListview
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

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<ShopModel>();
		new GetStoreList().execute();
	}

	class GetStoreList extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = "";
			String url = Constants.GET_SHOP_LIST;
			System.out.println(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
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
					ShopModel shop;
					for (int i = 0; i < jsons.size(); i++) {
						shop = new ShopModel();
						JSONObject item = jsons.getJSONObject(i);
						int sid = item.getIntValue("sId");
						String title = item.getString("title");
						String desc = item.getString("abstract");
						/*
						 * String thumbnailUrl = item.getString("thumbnailUrl")
						 * .replace("\\", "");
						 */
						String imageUrl = item.getString("imageUrl").replace(
								"\\", "");
						// shop.setThumbnailUrl(thumbnailUrl);
						shop.setImageUrl(imageUrl);
						shop.setSid(sid);
						shop.setTitle(title);
						shop.setDesc(desc);
						list.add(shop);
						adapter.notifyDataSetChanged();
					}
				} else {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		}

	}

	class StoreListAdapter extends BaseAdapter {

		class HolderView {
			TextView title;
			ImageView img;
			TextView desc;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HolderView holder;
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.store_list_item, null);
				holder.title = (TextView) convertView
						.findViewById(R.id.store_title);
				holder.img = (ImageView) convertView
						.findViewById(R.id.store_img);
				holder.desc = (TextView) convertView
						.findViewById(R.id.store_desc);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
				if (holder == null) {
					holder = new HolderView();
					convertView = inflater.inflate(R.layout.store_list_item,
							null);
					holder.title = (TextView) convertView
							.findViewById(R.id.store_title);
					holder.img = (ImageView) convertView
							.findViewById(R.id.store_img);
					holder.desc = (TextView) convertView
							.findViewById(R.id.store_desc);
					convertView.setTag(holder);
				}
			}
			holder.title.setText(list.get(position).getTitle());
			String desc = list.get(position).getDesc();
			if (desc.length() > 65) {
				desc = desc.substring(0, 60) + "...";
			}
			holder.desc.setText(desc);
			ImageLoader.getInstances().displayImage(
					list.get(position).getImageUrl(), holder.img);
			return convertView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.store_list_back_layout:
			finish();
			break;

		default:
			break;
		}
	}
}
