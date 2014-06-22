package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import com.danildr.androidcomponents.LoadingJSONfromURL;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;

public class SteamInventory extends FullScreenActivity {
	private JSONObject accountInfo;
	private String playerName;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (steamid == 0) {
			Intent nextActivity = new Intent(SteamInventory.this, SteamEnterAccount.class);
			nextActivity.putExtra("lang", lang);
			nextActivity.putExtra("classname", SteamInventory.class.toString());
			SteamInventory.this.startActivity(nextActivity);
		} else {
			setContentView(R.layout.activity_dota_webpage);
			String playerUrl = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + getString(R.string.steamkey) + "&steamids=" + steamid; 
			try {
				accountInfo = new LoadingJSONfromURL().execute(playerUrl).get(20, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException
					| TimeoutException e) {
				e.printStackTrace();
			}
			if (accountInfo != null) {
				try {
					JSONObject reponseJSON = accountInfo.getJSONObject("response");
					JSONObject playersJSON = reponseJSON.getJSONArray("players").getJSONObject(0);
					playerName = playersJSON.getString("personaname");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if (playerName != null) {
				String playerurl = "http://steamcommunity.com/id/" + playerName + "/inventory/" + getDota2LangLink() + "#570";
				WebView dotaPlayerItems = (WebView) findViewById(R.id.dotaWebView);
				dotaPlayerItems.getSettings().setJavaScriptEnabled(true);
				
				Button prevAcivity = (Button) findViewById(R.id.prevAcivity);
				prevAcivity.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						returnBack();
					}
				});

				dotaPlayerItems.setWebViewClient(new WebViewClient() {
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
							"#global_header, .games_list_tabs, .filter_ctn.inventory_filters, #footer, #footer_spacer, .view_inventory_logo { display: none; }" +
							"* { margin: 0;}" +
							".maincontent, #mainContents {width: 640px !important; padding: 0 !important; }" +
							".inventory_page_left, .inventory_page_right, .inventory_ctn, .trade_item_box, .view_inventory_page {width: 320px !important;}" +
							".profile_small_header_bg { margin-bottom: 0px !important; height: 100px !important; }" +
							"#mainContents { margin: 0px auto; }" +
							"</style>" + 
							"<meta name=\"viewport\" content=\"width=width-display, initial-scale=0.5, user-scalable=no\">' + document.getElementsByTagName('head')[0].innerHTML;");
						view.setVisibility(View.VISIBLE);
					}
				});
				
				dotaPlayerItems.loadUrl(playerurl);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		returnBack();
	}
	
	private void returnBack() {
		Intent showMm = new Intent(SteamInventory.this, Steam.class);
		showMm.putExtra("lang", lang);
		SteamInventory.this.startActivity(showMm);
	}

}
