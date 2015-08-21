package com.whut.activity;

import com.baidu.navisdk.comapi.tts.BTTSLibController;
import com.whut.activity.Fragment.MallFragment;
import com.whut.customer.R;
import com.whut.util.SelectImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCommentActivity extends Activity implements OnClickListener{
	
	private SelectImage selectImage;
	private ImageView addCommentImage;
	private EditText makeCommentText;
	private Button submitButton;
	public Bitmap temp = null;
	String path = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcomment);
		selectImage = new SelectImage(AddCommentActivity.this);
		selectImage.selectWay();
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		addCommentImage = (ImageView) findViewById(R.id.add_comment_image);
		makeCommentText = (EditText) findViewById(R.id.make_comment);
		submitButton = (Button) findViewById(R.id.submit_comment);
		addCommentImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectImage(v);
			}
		});
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String makeCommentString = makeCommentText.getText().toString();
				System.out.println("makeCommentString = "+makeCommentString);
				path = selectImage.imageFile.getPath();
				Intent intent = new Intent();
				intent.putExtra("makeCommentText", makeCommentString);
				intent.putExtra("commentPath", path);
				System.out.println("path ="+path);
				intent.putExtra("commentBitmap", temp);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	protected void selectImage(View v) {
		// TODO Auto-generated method stub
		selectImage.selectWay();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		temp = selectImage.getImage(requestCode, resultCode, data, true);
		if(temp!=null){
			System.out.println("temp != null");
			addCommentImage.setImageBitmap(temp);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_comment_back:
			finish();
			break;
		
		default:
			break;
		}
	}


	
	

}
