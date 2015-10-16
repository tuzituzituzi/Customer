package com.whut.activity;

import com.whut.customer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	
	private ImageView user_img;
	private TextView user_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		user_img = (ImageView) findViewById(R.id.user_img);
		user_name = (TextView) findViewById(R.id.user_name);
		Intent i = getIntent();
		String userName = i.getStringExtra("name");
		int userPic = i.getIntExtra("pic", R.drawable.user_pic1);
		user_img.setImageResource(userPic);
		user_name.setText(userName);
		
	}

}
