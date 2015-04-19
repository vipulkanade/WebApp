package com.example.webapp;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends ActionBarActivity {
	
	// creating a variable to store Webview
	private WebView webview;
	
	// creating a variable for progressBar
	private ProgressBar Pbar;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set's UI for your Activity
		setContentView(R.layout.activity_main);
		
		// Initialise variables from the layout mentioned above
		webview = (WebView) findViewById(R.id.customwebview);
		Pbar = (ProgressBar) findViewById(R.id.pB1);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// uses Javascript to run, by default it is false and won't run any JavaScript by default 
		webview.getSettings().setJavaScriptEnabled(true);
		
		// This is used so that progressbar can be shown to the user so he knows that some activity is happening.
		// If this is left out, after clicking on a new web link while navigating, it'll perform background loading 
		// and load page only after it is successfully loaded. Thus to engage user more of what is happening adding Progress Bar
		// If you don't want it, comment out the below code.
		webview.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress < 100 && Pbar.getVisibility() == ProgressBar.GONE){
                    Pbar.setVisibility(ProgressBar.VISIBLE);
                    webview.setVisibility(View.GONE);
                }
                Pbar.setProgress(newProgress);
                if(newProgress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                    webview.setVisibility(View.VISIBLE);
                }
			}
		});
		
		// This is used so it loads any further pages that you navigate to load in same view 
		// and not open new links in android browser
		webview.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		// Change URL here
		webview.loadUrl("http://www.google.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if (webview.copyBackForwardList().getCurrentIndex() > 0) {
			// as the page is there before closing activity navigate to it.
			webview.goBack();
		} else {
			// Your exit alert code, or alternatively line below to finish
			super.onBackPressed(); // finishes activity
		}		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}