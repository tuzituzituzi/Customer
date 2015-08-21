package com.whut.activity.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.whut.activity.PlaceActivity;
import com.whut.activity.GoodsDetailActivity;
import com.whut.customer.R;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MarketFragment extends Fragment {
	private Context context;
	private ListView listview;
	private List<String> list;
	private LayoutInflater inflater;
	private SquareAdapter adapter;
	private int data[] = { R.drawable.mall1, R.drawable.mall2,
			R.drawable.mall3, R.drawable.mall4, R.drawable.mall5,
			R.drawable.mall6 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater
				.inflate(R.layout.activity_market, container, false);
		initData();
		initView(view);
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<String>();
		list.add("重庆步行街");
		list.add("沙坪坝商圈");
		list.add("解放碑商圈");
		list.add("观音桥商圈");
		list.add("南坪商圈");
		list.add("杨家坪商圈");
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		context = getActivity();
		listview = (ListView) v.findViewById(R.id.Square_ListView);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new SquareAdapter();
		listview.setAdapter(adapter);
		enterStoreDetail();
	}

	private void enterStoreDetail() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, PlaceActivity.class);
				startActivity(intent);
			}
		});
	}

	class SquareAdapter extends BaseAdapter {
		private HolderView holder;

		class HolderView {
			TextView square_name;
			ImageView image;
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
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.square_item, null);
				holder.square_name = (TextView) convertView
						.findViewById(R.id.market_item_name);
				holder.image = (ImageView) convertView
						.findViewById(R.id.market_item_img);
				convertView.setTag(holder);
			} else
				holder = (HolderView) convertView.getTag();
			holder.square_name.setText(list.get(position));
			holder.image.setImageResource(data[position]);

			return convertView;
		}

	}
}
