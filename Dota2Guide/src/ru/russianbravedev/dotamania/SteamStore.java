package ru.russianbravedev.dotamania;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import ru.russianbravedev.dotamania.R;

public class SteamStore extends FullScreenActivity {
	private WebView dotaWebWV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dota_webpage);
		String dotaStoreUrl = "http://www.dota2.com/store/";
		
		dotaWebWV = (WebView) findViewById(R.id.dotaWebView);

		dotaWebWV.getSettings().setJavaScriptEnabled(true);
		dotaWebWV.getSettings().setLoadWithOverviewMode(true);

		Button prevAcivity = (Button) findViewById(R.id.prevAcivity);
		prevAcivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		dotaWebWV.setWebViewClient(new WebViewClient() {
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
				view.loadUrl("javascript:document.getElementsByTagName('head')[0].innerHTML+='<style>" +
					"#navBarBGRepeat, #StoreTabs, #StoreFilters, .ValveFooter, .PartnerBanner, .ButtonsAndPriceContainer, .Recommendations, .RecommendedContainer { display: none !important; }" +
					".StoreContainer, .StoreContainerBg, .MainItemContainer .ItemContainerRow, .ItemContainerBg, .MainItemContainer .Grid { width: auto; }" +
					".MainItemContainer .ItemContainerCell {width: 180px !important; padding: 0px !important; }" +
					".ItemContainerCell .ItemImage img {width: 180px; height: 120px; }" +
					".PreviewContainer, .RecommendedContainer { max-width: auto; }" +
					".PreviewContainer { padding: 0 }" +
					"#PreviewSubContainerRight { float: none; padding: 0; width: auto; }" +
					"</style>" +
					"<meta name=\"viewport\" content=\"width=480px, initial-scale=0.5, user-scalable=no\">'");
				view.setVisibility(View.VISIBLE);
			}
		});
			
		
		dotaStoreUrl += getDota2LangLink() + "#cat=3634838168";
		
		dotaWebWV.loadUrl(dotaStoreUrl);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(dotaWebWV.canGoBack()){
                	dotaWebWV.goBack();
                }else{
                	returnBack();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	
	private void returnBack() {
		Intent showMm = new Intent(SteamStore.this, Steam.class);
		showMm.putExtra("lang", lang);
		SteamStore.this.startActivity(showMm);		
	}
}
