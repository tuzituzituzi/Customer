package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjie.imageloadersample.imageloader.ImageLoader;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.customer.R.layout;
import com.whut.data.model.GoodsDetailModel;
import com.whut.data.model.GoodsModel;
import com.whut.util.JsonUtils;
import com.whut.util.WebHelper;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailActivity extends Activity implements OnClickListener {

	private Context context;
	private GoodsDetailModel goods_detail;
	private GoodsModel goods;
	private String goods_id;
	private TextView title_bar;
	private TextView title;
	private ImageView goods_img;
	private TextView desc;
	private ImageView return_overdate_img;
	private ImageView return_anytime_img;
	private TextView return_overdate_tv;
	private TextView return_anytime_tv;
	private TextView remain_time;
	private TextView old_prive_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail);
		context = this;
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		title_bar = (TextView) findViewById(R.id.goods_detail_titlebar);
		title = (TextView) findViewById(R.id.goods_detail_title);
		goods_img = (ImageView) findViewById(R.id.goods_detail_img);
		desc = (TextView) findViewById(R.id.goods_detail_desc);
		return_overdate_img = (ImageView) findViewById(R.id.return_overdate_icon);
		//return_anytime_img = (ImageView) findViewById(R.id.return_anytime_icon);
		return_overdate_tv = (TextView) findViewById(R.id.return_overdate_tv);
		//return_anytime_tv = (TextView) findViewById(R.id.return_anytime_tv);
		remain_time = (TextView) findViewById(R.id.remain_time_tv);
		old_prive_tv = (TextView) findViewById(R.id.old_prive_tv);
		old_prive_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
	}

	private void initData() {
		// TODO Auto-generated method stub
		goods = (GoodsModel) getIntent().getSerializableExtra("goods");
		goods_detail = new GoodsDetailModel();
		if (goods != null)
			new GetGoodsDetail().execute(goods.getGid());
	}

	class GetGoodsDetail extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			goods_id = params[0];
			System.out.println(goods_id + "----------------");
			String result = "";
			String url = Constants.GET_GOODS_DETAIL;
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("mall", "0"));
			list.add(new BasicNameValuePair("gId", goods_id));
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
				System.out.println(result);
				int code = json.getIntValue("code");
				String msg = json.getString("msg");
				if (code == 1) {
					JSONObject item = json.getJSONObject("data");
					title_bar.setText(item.getString("title"));
					//title.setText(item.getString("title"));
					title.setText("JackJones杰克琼斯");
					ImageLoader.getInstances().displayImage(
							item.getString("imageUrl"), goods_img);
					desc.setText(item.getString("desc"));
					//return_anytime_tv.setText(item.getIntValue("isReturnAnytime:") == 1 ? Constants.CAN_RETURN_ANYTIME: Constants.CANNOT_RETURN_ANYTIME);
					//return_overdate_tv.setText(item.getIntValue("isReturnOverdate:") == 1 ? Constants.CAN_RETURN_OVERDATE: Constants.CANNOT_RETURN_OVERDATE);
				} else {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.share_but:
			
			break;

		default:
			finish();
		}
		
		
	}

}
