package ru.russianbravedev.dotamania;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import ru.russianbravedev.dotamania.R;

public class DotaBuffInfo extends FullScreenActivity {
	private WebView dotaBuffVW;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dota_buff_info);
		dotaBuffVW = (WebView) findViewById(R.id.dotaBuffVW);
		String menustr = getIntent().getStringExtra("menustr");
		String dotabuffurl = "";
		
		// настройка WebView
		dotaBuffVW.getSettings().setJavaScriptEnabled(true);
		dotaBuffVW.getSettings().setUseWideViewPort(false);
		dotaBuffVW.getSettings().setLoadWithOverviewMode(true);
		dotaBuffVW.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		
		// отображение progressdialog
		progress = new ProgressDialog(this);
		progress.setTitle(getString(R.string.loading));
		progress.setMessage(getString(R.string.waitloading));
		
		// кнопка перехода на предыдущую
		Button prevAcivity = (Button) findViewById(R.id.prevAcivity);
		prevAcivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		dotaBuffVW.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
			    return false;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progress.show();
				super.onPageStarted(view, url, favicon);
				view.setVisibility(View.GONE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:document.getElementsByTagName('head')[0].innerHTML='<style>" + 
					"#logo, #page-nav, #search-nav, #session-nav, #content-interactive, .notifications, .leaderboard, .medium_rectangle, #leaderboard_b, .short_rectangle, #container-footer, .more, .avatars, .tableAd {display:none !important;}" + 
					".hero-show .secondary, #content-header-primary { float: none; } " + 
					"body, body > *,  #container-content  { background-color: #000; }"+
					"#container-content #page-content, #container-header #page-header, .hero-show .primary, .item-show .primary { width: auto !important; }" +
					".skill-choices.smaller .skill .line .entry { width: 15px !important; }" +
					"#container-header h1 { font-size: 1em; }" +
					".hero-grid>a { float: left; } " +
					"#container-header #page-header #content-header #content-header-secondary { position: initial !important; }" +
					"#container-header #page-header #content-header { height: auto !important; }" + 
					"#container-header #page-header #content-header #content-header-primary, #container-header #page-header #content-header #content-header-secondary { width: 100%; }" +
					".premium.unavailable { display: none; }" +
					"</style>" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">' + document.getElementsByTagName('head')[0].innerHTML;");
				progress.dismiss();
				view.setVisibility(View.VISIBLE);
			}
		});
		
		switch (lang) {
			case "ru_RU":
				dotabuffurl = "http://ru.dotabuff.com/" + menustr;
				break;
			case "de_DE":
				dotabuffurl = "http://de.dotabuff.com/" + menustr;
				break;
			case "pt_BR":
				dotabuffurl = "http://pt.dotabuff.com/" + menustr;
				break;
			case "zh_CN":
				dotabuffurl = "http://zh.dotabuff.com/" + menustr;
				break;
			default:
				dotabuffurl = "http://dotabuff.com/" + menustr;
				break;
		};
		dotaBuffVW.loadUrl(dotabuffurl);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(dotaBuffVW.canGoBack()){
                	dotaBuffVW.goBack();
                }else{
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
