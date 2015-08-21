package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.a.a.a.c;
import com.whut.customer.R;
import com.whut.customer.R.drawable;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FocusListActivity extends Activity implements OnClickListener {

	private Context context;
	private List<String> list;
	private ListView listview;
	private LayoutInflater inflater;
	private FocusListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_focus_list);
		initData();
		InitView();
	}

	private void InitView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.Focus_ListView);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new FocusListAdapter();
		listview.setAdapter(adapter);
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
		list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
	}

	class FocusListAdapter extends BaseAdapter {
		class HolderView {
			ImageView head_icon;
			TextView name;
			LinearLayout layout;
			TextView isFocus;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final HolderView holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.focus_listitem, null);
				holder = new HolderView();
				holder.head_icon = (ImageView) convertView
						.findViewById(R.id.focus_listitem_img);
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.focus_listitem_isfollow_layout);
				holder.isFocus = (TextView) convertView
						.findViewById(R.id.focus_listitem_isfollow);
				holder.layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*
						 * Toast.makeText(context, String.valueOf(position),
						 * Toast.LENGTH_SHORT).show();
						 */
						switchFocusIcon(v);
					}

					private void switchFocusIcon(View v) {
						// TODO Auto-generated method stub
						if ("1".endsWith(v.getTag().toString())) {
							holder.isFocus.setText("未关注");
							holder.isFocus.setTextColor(getResources()
									.getColor(R.color.white));
							holder.layout.setTag("0");
							holder.layout.setBackgroundColor(getResources()
									.getColor(R.color.title));
						} else {
							holder.isFocus.setText("已关注");
							holder.layout.setTag("1");
							holder.isFocus.setTextColor(getResources()
									.getColor(R.color.black));
							holder.layout.setBackgroundColor(getResources()
									.getColor(R.color.isFocus_bg));
						}
					}
				});
				holder.name = (TextView) convertView
						.findViewById(R.id.focus_listitem_name);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (position % 2 == 0) {
				holder.isFocus.setText("未关注");
				holder.isFocus.setTextColor(getResources().getColor(
						R.color.white));
				holder.layout.setTag("0");
				holder.layout.setBackgroundColor(getResources().getColor(
						R.color.title));
			} else {
				holder.isFocus.setText("已关注");
				holder.layout.setTag("1");
				holder.isFocus.setTextColor(getResources().getColor(
						R.color.black));
				holder.layout.setBackgroundColor(getResources().getColor(
						R.color.isFocus_bg));
			}
			return convertView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.focuslist_back_layout:
			finish();
			break;

		default:
			break;
		}
	}
}
