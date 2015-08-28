package com.whut.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.data.model.CommentModel;
import com.whut.data.model.DynamicModel;
import com.whut.data.model.GoodsModel;
import com.whut.util.JsonUtils;
import com.whut.util.WebHelper;

public class SquareDetailActivity extends Activity implements OnClickListener {


	private ListView listview;
	private LayoutInflater inflater;
	private SquareCommentAdapter adapter;
	private Context context;
	private DynamicModel dynamic;
	private View view;
	private ImageView image;
	private ImageView uPic;
	private TextView uName;
	private TextView location;
	private TextView store;
	private TextView desc;
	private TextView visitCount;
	private TextView releaseTime;
	private ImageView good_icon;
	private TextView good_num;
	private TextView comment_num;
	private List<CommentModel> commentList;

	private CommentModel myComment ;
	private CommentAsyncTask commentAsyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_square_detail);
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		context = this;
		listview = (ListView) findViewById(R.id.Square_Comment_ListView);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new SquareCommentAdapter();
		listview.setAdapter(adapter);
		view = inflater.inflate(R.layout.activity_square_detail, null);
	}

	private void initData() {
		// TODO Auto-generated method stub
		dynamic = (DynamicModel) getIntent().getSerializableExtra("dynamic");
		commentList = new ArrayList<CommentModel>();
//		CommentModel comment = new CommentModel("1","1","1",R.drawable.user_pic4,"我也去过了，味道特别棒！！很喜欢","2015年8月25日","jiang");
//		commentList.add(comment);
//		CommentModel comment1 = new CommentModel("1","1","1",R.drawable.user_pic1,"我也去过了，hahahahah！很喜欢","2015年8月25日","jiang");
//		commentList.add(comment1);
		commentAsyncTask = new CommentAsyncTask();
		commentAsyncTask.execute();
//		list = new ArrayList();
//		
//		list.add("很好，下次还会来");
//		list.add("好评！！！");
//		list.add("GOOD！！");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.square_comment_back_layout:
			finish();
			break;
		case R.id.square_comment_area:
			new SquareCommentPopupWindow(context,
					findViewById(R.id.square_detail_layout));
			break;
		case R.id.square_good_img:
			swichGoodIcon(v);
			break;
		}
	}

	private void swichGoodIcon(View v) {
		// TODO Auto-generated method stub
		ImageView view = (ImageView) v;
		int num = Integer.parseInt(good_num.getText().toString());
		if ("0".equals(view.getTag())) {
			view.setImageResource(R.drawable.gooded_icon);
			view.setTag("1");
			num++;
			good_num.setText(String.valueOf(num));
		} else {
			view.setImageResource(R.drawable.good_icon);
			view.setTag("0");
			num--;
			good_num.setText(String.valueOf(num));
		}
	}

	class SquareCommentPopupWindow extends PopupWindow {
		public SquareCommentPopupWindow(Context context, View parent) {
			final View view = View.inflate(context,
					R.layout.post_comment_popupwindow, null);
			view.findViewById(R.id.post_commnet_blank_view).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (isShowing()) {
								dismiss();
							}
						}
					});
			view.findViewById(R.id.post_comment_but).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String txt = ((EditText) view
									.findViewById(R.id.square_comment_edit))
									.getText().toString();
							myComment = new CommentModel();
							myComment.setcId("3");
							myComment.setComment(txt);
							SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
							String data = sDateFormat.format(new java.util.Date());
							myComment.setData(data);
							myComment.setdId(dynamic.getdId());
							myComment.setuId(dynamic.getuId());
							myComment.setuName("jiangshan");
							myComment.setuPic(R.drawable.user_pic7);
							commentList.add(myComment);
							System.out.println("2、commentList.size() = "+commentList.size());
							adapter.notifyDataSetChanged();
							listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
							dismiss();
						}
					});
			view.startAnimation(AnimationUtils.loadAnimation(context,
					R.anim.push_bottom_in_2));
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setOutsideTouchable(true);
			setFocusable(true);
			setContentView(view);

			showAtLocation(parent, Gravity.TOP, 0, 0);

			update();
		}
	}
	
	

	

	class SquareCommentAdapter extends BaseAdapter {
		private HolderView holder;

		class HolderView {
//			TextView comment_tv;
			ImageView comment_user_pic ;
			TextView comment_user_name ;
			TextView comment_content ;
			TextView comment_data ; 
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return commentList.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return commentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			System.out.println("position-------------->" + position);

			if (position == 0) {
				convertView = inflater.inflate(R.layout.square_listitem, null);
				initView(convertView);
			} else {
				CommentModel comment = commentList.get(position-1);
				if (convertView == null) {
					holder = new HolderView();
					convertView = inflater.inflate(
							R.layout.square_comment_item, null);
					holder.comment_user_pic = (ImageView) convertView.findViewById(R.id.comment_user_pic);
					holder.comment_user_name = (TextView) convertView.findViewById(R.id.comment_user_name);
					holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
					holder.comment_data = (TextView) convertView.findViewById(R.id.comment_data);
					convertView.setTag(holder);
				} else {
					holder = (HolderView) convertView.getTag();
					if (holder == null) {
						holder = new HolderView();
						convertView = inflater.inflate(
								R.layout.square_comment_item, null);
						holder.comment_user_pic = (ImageView) convertView.findViewById(R.id.comment_user_pic);
						holder.comment_user_name = (TextView) convertView.findViewById(R.id.comment_user_name);
						holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
						holder.comment_data = (TextView) convertView.findViewById(R.id.comment_data);
						convertView.setTag(holder);
					}
				}
				holder.comment_user_name.setText(comment.getuName());
				holder.comment_user_pic.setImageResource(comment.getuPic());
				holder.comment_content.setText(comment.getComment());
				holder.comment_data.setText(comment.getData());
			}
			return convertView;
		}

		private void initView(View v) {
			// TODO Auto-generated method stub
			good_icon = (ImageView) v.findViewById(R.id.square_good_img);
			image = (ImageView) v.findViewById(R.id.square_item_img);
			uPic = (ImageView) v.findViewById(R.id.square_user_head);
			uName = (TextView) v.findViewById(R.id.square_user_name);
			good_num = (TextView) v.findViewById(R.id.square_good_count);
			comment_num = (TextView) v.findViewById(R.id.square_comment_count);
			location = (TextView) v.findViewById(R.id.square_store_area);
			store = (TextView) v.findViewById(R.id.square_store_name);
			desc = (TextView) v.findViewById(R.id.square_item_desc);
			visitCount = (TextView) v.findViewById(R.id.square_msg_visit_count);
			releaseTime = (TextView) v.findViewById(R.id.square_msg_posttime);

			image.setImageResource(Integer.parseInt(dynamic.getPhotoUrl()));
			uPic.setImageResource(Integer.parseInt(dynamic.getuPicUrl()));
			uName.setText(dynamic.getuName());
			good_num.setText(String.valueOf(dynamic.getLikeCount()));
			comment_num.setText(String.valueOf(dynamic.getCommentCount()));
			location.setText(dynamic.getLocation());
			store.setText(dynamic.getgName());
			desc.setText(dynamic.getDesc());
			visitCount.setText(String.valueOf(dynamic.getVisitCount()));
			releaseTime.setText(dynamic.getReleaseTime());
		}
	}
	
	class CommentAsyncTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = "";
			String url = Constants.GET_GOODS_LIST;
			System.out.println(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("sId", "1"));
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
			String json = "[{'cId':'1','dId':'1','uId':'3','uPic':'"+String.valueOf(R.drawable.user_pic3)+"','comment':'我也去过了，味道特别棒！！很喜欢','data':'2012年7月3日','uName':'jiang'},{'cId':'1','dId':'1','uId':'3','uPic':'"+String.valueOf(R.drawable.user_pic6)+"','comment':'好好吃哟！','data':'2012年7月3日','uName':'jiang'}]";
			JSONArray jsonArray = JSONArray.parseArray(json);
			
			System.out.println("jsonArray = "+jsonArray );
			System.out.println("jsonArray.size() = "+jsonArray.size());
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String cid = jsonObject.getString("cId");
				String did = jsonObject.getString("dId");
				String uid = jsonObject.getString("uId");
				int upic = jsonObject.getIntValue("uPic");
				String comment = jsonObject.getString("comment");
				String data = jsonObject.getString("data");
				String uname = jsonObject.getString("uName");
				System.out.println("cid = "+cid + "upic = "+upic);
				CommentModel comments = new CommentModel();
				comments.setcId(cid);
				comments.setdId(did);
				comments.setuId(uid);
				comments.setData(data);
				comments.setuName(uname);
//				comments.setuPic(R.drawable.user_pic6);
				comments.setuPic(upic);
				comments.setComment(comment);
//				CommentModel comments = JSON.toJavaObject(jsonObject, CommentModel.class);
				
				commentList.add(comments);
			}
			System.out.println("commentlist.size() ="+commentList.size() );
			adapter.notifyDataSetChanged();
//			
		
	}


	}
}
