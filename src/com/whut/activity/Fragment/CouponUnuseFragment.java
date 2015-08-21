package com.whut.activity.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.wangjie.imageloadersample.imageloader.ImageLoader;
import com.whut.customer.R;
import com.whut.data.model.CouponModel;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CouponUnuseFragment extends Fragment {
	private List<CouponModel> list;
	private LayoutInflater inflater;
	private ListView listview;
	private Context context;
	private CouponUnuseAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.coupon_listview, container, false);
		initData();
		initView(view);
		return view;
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		// TODO Auto-generated method stub
		list = (ArrayList<CouponModel>) getArguments().getSerializable("data");
		System.out.println("ssssssssssssssssssssss" + list.size());
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		context = getActivity();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new CouponUnuseAdapter();
		listview = (ListView) v.findViewById(R.id.Coupon_ListView);
		listview.setAdapter(adapter);
	}

	class CouponUnuseAdapter extends BaseAdapter {
		class HolderView {
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
				convertView = inflater
						.inflate(R.layout.unuse_coupon_item, null);
				holder.desc = (TextView) convertView
						.findViewById(R.id.unuse_coupon_desc);
				holder.img = (ImageView) convertView
						.findViewById(R.id.unuse_coupon_icon);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			holder.desc.setText(list.get(position).getTitle() + ":"
					+ list.get(position).getDesc());
			ImageLoader.getInstances().displayImage(
					list.get(position).getThumbnailUrl(), holder.img);
			return convertView;
		}
	}
}
