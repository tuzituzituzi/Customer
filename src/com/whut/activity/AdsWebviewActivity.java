package com.whut.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.whut.customer.R;
import com.whut.customer.R.id;
import com.whut.customer.R.layout;
import com.whut.util.WVJBWebViewClient;
import com.whut.util.WVJBWebViewClient.WVJBResponseCallback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AdsWebviewActivity extends Activity implements OnClickListener {
	private Context context;
	private WebView web;
	private String url;
	private WVJBWebViewClient webViewClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
		initData();
		intiView();
	}

	private void intiView() {
		// TODO Auto-generated method stub
		web = (WebView) findViewById(R.id.webview);
		//支持javascript
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebChromeClient(new WebChromeClient());
		//覆盖WebView默认的通过其他浏览器打开网页的行为，使网页在程序中打开
		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
		webViewClient = new MyWebViewClient(web);
		webViewClient.enableLogging();
		web.setWebViewClient(webViewClient);
		web.loadUrl(url);
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
		// url = "file:///android_asset/ExampleApp.html";
		url = getIntent().getStringExtra("ads_url");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ads_view_back_layout:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
			//返回上一级页面
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyWebViewClient extends WVJBWebViewClient {
		public MyWebViewClient(WebView webView) {

			// support js send
			super(webView, new WVJBHandler() {

				@Override
				public void request(Object data, WVJBResponseCallback callback) {
					Toast.makeText(context,
							"ObjC Received message from JS:" + data,
							Toast.LENGTH_LONG).show();
					callback.callback("Response for message from ObjC!");
				}
			});

			/*
			 * // not support js send super(webView);
			 */

			enableLogging();

			registerHandler("testObjcCallback", new WVJBHandler() {

				@Override
				public void request(Object data, WVJBResponseCallback callback) {
					Toast.makeText(context, "testObjcCallback called:" + data,
							Toast.LENGTH_LONG).show();
					callback.callback("Response from testObjcCallback!");
					
				}
			});

			send("A string sent from ObjC before Webview has loaded.",
					new WVJBResponseCallback() {

						@Override
						public void callback(Object data) {
							Toast.makeText(context,
									"ObjC got response! :" + data,
									Toast.LENGTH_LONG).show();
						}
					});

			try {
				callHandler("testJavascriptHandler", new JSONObject(
						"{\"foo\":\"before ready\" }"),
						new WVJBResponseCallback() {

							@Override
							public void callback(Object data) {
								Toast.makeText(
										context,
										"ObjC call testJavascriptHandler got response! :"
												+ data, Toast.LENGTH_LONG)
										.show();
							}
						});
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void executeJavascript(String script) {
			// TODO Auto-generated method stub
			super.executeJavascript(script);
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);
		}

	}
}
