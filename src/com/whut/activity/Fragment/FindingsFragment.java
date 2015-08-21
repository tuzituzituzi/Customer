package com.whut.activity.Fragment;

import com.whut.customer.R;
import com.whut.customer.R.layout;
import com.whut.qrcode.CaptureActivity;

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
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FindingsFragment extends Fragment implements OnClickListener{
	private Intent tempIntent;
	private Context context;
	private TextView findings_scan_title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_findings, container, false);
		context = getActivity();
		initView(view);
		return view ;
	}
	private void initView(View v) {
		// TODO Auto-generated method stub
		findings_scan_title=(TextView) v.findViewById(R.id.findings_scan_title);
		findings_scan_title.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.findings_scan_title:
			tempIntent=new Intent(context,CaptureActivity.class); 
			startActivity(tempIntent); 
			break;
		default:
			break;
		}
	}
}
