package com.devtee.olxtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ContentActivity extends Activity{

	public static final String TAG_URL = "url";
	public static final String TAG_TITLE = "titls";
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_content);
		
		webView = (WebView) findViewById(R.id.webView);
		
		Bundle bundle = getIntent().getExtras();
		String url = bundle.getString(TAG_URL);
		String title = bundle.getString(TAG_TITLE);
		
		setTitle(title);
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebChromeClient(new CustomWebChromeClient());
		webView.loadUrl(url);
		
	}
	
	private class CustomWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			
			ContentActivity.this.setProgress(newProgress * 100);
		}
		
	}
}
