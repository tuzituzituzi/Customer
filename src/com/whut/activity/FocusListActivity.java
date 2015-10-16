package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.a.a.a.c;
import com.whut.customer.R;
import com.whut.customer.R.drawable;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class FocusListActivity extends Activity implements OnClickListener {



	private Context context;
	private List<FocusModel> list;
	private ListView listview;
	private LayoutInflater inflater;
	private FocusListAdapter adapter;
	private FocusAsynctask focusAsynctask;

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
		list = new ArrayList<FocusModel>();

		focusAsynctask = new FocusAsynctask();
		focusAsynctask.execute(-1);
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

		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
//			// TODO Auto-generated method stub
//			final HolderView holder;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.focus_listitem, null);
//				holder = new HolderView();
//				holder.head_icon = (ImageView) convertView
//						.findViewById(R.id.focus_listitem_img);
//				holder.layout = (LinearLayout) convertView
//						.findViewById(R.id.focus_listitem_isfollow_layout);
//				holder.isFocus = (TextView) convertView
//						.findViewById(R.id.focus_listitem_isfollow);
//				holder.layout.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						/*
//						 * Toast.makeText(context, String.valueOf(position),
//						 * Toast.LENGTH_SHORT).show();
//						 */
//						switchFocusIcon(v);
//
//						//点击即不关注此人
//						focusAsynctask = new FocusAsynctask();
//						focusAsynctask.execute(position);
//					}
//
//					private void switchFocusIcon(View v) {
//						// TODO Auto-generated method stub
//						if (!list.get(position).isuFlag()) {
//							holder.isFocus.setText("未关注");
//							holder.isFocus.setTextColor(getResources()
//									.getColor(R.color.white));
//							holder.layout.setTag("0");
//							holder.layout.setBackgroundColor(getResources()
//									.getColor(R.color.title));
//						} else {
//							holder.isFocus.setText("已关注");
//							holder.layout.setTag("1");
//							holder.isFocus.setTextColor(getResources()
//									.getColor(R.color.black));
//							holder.layout.setBackgroundColor(getResources()
//									.getColor(R.color.isFocus_bg));
//						}
//					}
//				});
//				holder.name = (TextView) convertView
//						.findViewById(R.id.focus_listitem_name);
//				convertView.setTag(holder);
//			} else {
//				holder = (HolderView) convertView.getTag();
//			}
//			if (!list.get(position).isuFlag()) {
//				holder.isFocus.setText("未关注");
//				holder.isFocus.setTextColor(getResources().getColor(
//						R.color.white));
//				holder.layout.setTag("0");
//				holder.layout.setBackgroundColor(getResources().getColor(
//						R.color.title));
//			} else {
//				holder.isFocus.setText("已关注");
//				holder.layout.setTag("1");
//				holder.isFocus.setTextColor(getResources().getColor(
//						R.color.black));
//				holder.layout.setBackgroundColor(getResources().getColor(
//						R.color.isFocus_bg));
//			}
			
			HolderView holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.focus_listitem, null);
				holder = new HolderView();
				holder.head_icon = (ImageView) convertView
						.findViewById(R.id.focus_listitem_img);
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.focus_listitem_isfollow_layout);
				holder.isFocus = (TextView) convertView
						.findViewById(R.id.focus_listitem_isfollow);
				holder.name = (TextView) convertView.findViewById(R.id.focus_listitem_name);
				
				holder.layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showDialog(position);
					}
				});
				
				convertView.setTag(holder);
				
			}else{
				holder = (HolderView) convertView.getTag();
			}
			
			holder.head_icon.setImageResource(list.get(position).getuPic());
			holder.name.setText(list.get(position).getuName());
			holder.layout.setBackgroundColor(getResources().getColor(R.color.isFocus_bg));
			return convertView;
		}

		protected void showDialog(final int position) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new Builder(FocusListActivity.this);
			builder.setMessage("确定取消关注 "+list.get(position).getuName()+"?");
			builder.setTitle("提示");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					focusAsynctask = new FocusAsynctask();
					focusAsynctask.execute(position);
					
				}

			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
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
	

	public class FocusAsynctask extends AsyncTask<Integer, Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			if(params[0]== -1){
				//获取关注列表
				System.out.println("get focus");
			}else {
				//从关注列表删除
				FocusModel f = list.get(params[0]);
				System.out.println("delete from focus "+ f.getuName());
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(Integer result) {
			if(list == null){
				return;
			}else if(result == -1){

				System.out.print("result = "+result);
				//从服务器获取关注列表
				list.clear();
				FocusModel f = new FocusModel();
				f.setuName("哈哈哈");
				f.setuFlag(true);
				f.setuPic(R.drawable.user_pic1);
				list.add(f);
				f = new FocusModel();
				f.setuName("呵呵");
				f.setuFlag(true);
				f.setuPic(R.drawable.user_pic2);
				list.add(f);
				f = new FocusModel();
				f.setuName("拉拉");
				f.setuFlag(true);
				f.setuPic(R.drawable.user_pic3);
				list.add(f);
			}
			else {

				System.out.print("result1 = "+result);
				list.remove(list.get(result));
				
			}
			System.out.println("list.size() ="+ list.size());
			adapter.notifyDataSetChanged();
		}

	}
	

	public class FocusModel {
		String uName;
		int uPic;
		boolean uFlag;
		
		public String getuName() {
			return uName;
		}
		public void setuName(String uName) {
			this.uName = uName;
		}
		
		public int getuPic() {
			return uPic;
		}
		public void setuPic(int uPic) {
			this.uPic = uPic;
		}
		public boolean isuFlag() {
			return uFlag;
		}
		public void setuFlag(boolean uFlag) {
			this.uFlag = uFlag;
		}
		
	}
	
	
}
