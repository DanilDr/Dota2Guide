package ru.russianbravedev.dotamania;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import ru.russianbravedev.dotamania.R;

public class DotaItemsGame extends FullScreenActivity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dota_webpage);
		String dotaItemsGameUrl = "http://www.dota2.com/quiz";
		WebView dotaItemsGame = (WebView) findViewById(R.id.dotaWebView);

		dotaItemsGame.getSettings().setJavaScriptEnabled(true);
		dotaItemsGame.getSettings().setLoadWithOverviewMode(true);

		Button prevAcivity = (Button) findViewById(R.id.prevAcivity);
		prevAcivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		dotaItemsGame.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
			    return false;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				view.setVisibility(View.GONE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:document.getElementsByTagName('head')[0].innerHTML='<style>" +
					"#bodyContainer { background: none !important; width: auto !important; height: auto !important; }" +
					"#navBarBGRepeat { display: none; }" +
					"#mainContent { top: 0px !important; background: none !important; box-shadow: none !important; width: auto !important; }" +
					"#alertMsg {width: 70% !important; border: initial !important; border-image: initial !important; left: 15% !important;}" +
					"#sourceItems {width: 320px; }" +
					"</style>" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">' + document.getElementsByTagName('head')[0].innerHTML;");
				view.setVisibility(View.VISIBLE);
			}
		});
		
		
		dotaItemsGameUrl += getDota2LangLink();
		
		dotaItemsGame.loadUrl(dotaItemsGameUrl);
	}
}