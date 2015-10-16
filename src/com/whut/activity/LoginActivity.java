package com.whut.activity;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.baidu.navisdk.util.common.Utils;
import com.whut.config.Constants;
import com.whut.customer.R;
import com.whut.data.model.UserModel;
import com.whut.util.WebHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	

	Button btn_login;
	EditText edit_phone,edit_valid;
	private String username;
	private String password;
	private UserModel MyModel;
	private LoginAsyncTask loginAsyncTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edit_phone = (EditText) findViewById(R.id.login_phone);
		edit_valid = (EditText) findViewById(R.id.login_valid);
//		btn_valid = (Button) findViewById(R.id.login_getvalid);
		btn_login = (Button) findViewById(R.id.login_btn);
//		btn_valid.setOnClickListener(listener);
		btn_login.setOnClickListener(listener);
		MyModel = new UserModel();
		
		if(TextUtils.isEmpty(edit_phone.getText())){
			
		}
	}

	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
//			case R.id.login_getvalid:
//				String str = "123456";
//				Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
//				btn_valid.setSelected(true);
//				btn_valid.setTextColor(Color.rgb(173, 173, 173));
//				
//				break;
			
			case R.id.login_btn:
				
				username = edit_phone.getText().toString();
				password = edit_valid.getText().toString();
				loginAsyncTask = new LoginAsyncTask();
				loginAsyncTask.execute();
				

			default:
				break;
			}
			
		}
	};

	
	public class LoginAsyncTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = "";
			String url = Constants.GET_GOODS_LIST;
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			//验证用户名和密码
			list.add(new BasicNameValuePair("UserName", username));
			list.add(new BasicNameValuePair("Password", password));
			try {
				
//				result = WebHelper.getJsonString(url, list);
				
				System.out.println(result);
				result = "{\"uToken\":\"abcdefghijklmn\",\"uId\":\"1\",\"uName\":\"jiangshan\",\"uPicUrl\":\"R.drawable.user_pic3\",\"uPassword\":\"123456\"}";
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//获取用户信息，保存进MyModel
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			JSONObject item = JSONObject.parseObject(result);
			MyModel.setuToken(item.getString("uToken"));
			MyModel.setuId(item.getString("uId"));
			MyModel.setuName(item.getString("uName"));
			MyModel.setuPicUrl(item.getString("uPicUrl"));
			MyModel.setuPassword(item.getString("uPassword"));
			System.out.println(MyModel.getuId()+MyModel.getuName()+MyModel.getuPassword());
			if(MyModel.getuToken()!=null){
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			}
			
		}
		
		
		

	}
	
}
