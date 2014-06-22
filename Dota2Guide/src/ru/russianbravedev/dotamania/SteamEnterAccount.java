package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SteamEnterAccount extends FullScreenActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steam_enter_account);
		
		Button steamQueryBut = (Button) findViewById(R.id.steamQueryBut);
		
		steamQueryBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Long entersteamid = null;
				Boolean checkStatus = false;
				EditText steamNameID = (EditText) findViewById(R.id.steamNameID);
				String inputStr = steamNameID.getText().toString();//steamNameID.getText().toString(); // "76561197960435530" //  "justmitya" // "76561197972495328"
				if (inputStr.length() > 0) {
					try {
						entersteamid = new GetSteamIDbyName().execute(inputStr).get(10000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						e.printStackTrace();
					}
					
					if (entersteamid == null && checkString(inputStr)) { 
						entersteamid = new Long(inputStr); 
						try {
							checkStatus = new CheckSteamID().execute(entersteamid.toString(), getString(R.string.steamkey)).get(10000, TimeUnit.MILLISECONDS);
						} catch (InterruptedException | ExecutionException | TimeoutException e) {
							e.printStackTrace();
						}
						if (checkStatus) { 
							showNext(entersteamid); 
						} else {
							Toast.makeText(SteamEnterAccount.this, getString(R.string.erroraccount), Toast.LENGTH_SHORT).show();
						}
					} else {
						showNext(entersteamid);					
					};
				} else {
					String errormessage = getString(R.string.errorenteridorname);
					int duration = Toast.LENGTH_SHORT;
					Toast.makeText(SteamEnterAccount.this, errormessage, duration).show();
				}
			}
		});
	}
	
	private void showNext(Long steamid) {
		Class<?> nextclass = null;
		SharedPreferences.Editor editor = settings.edit();
		if (steamid == null) { steamid = new Long(0); }
		editor.putLong("steamid", steamid);
		editor.commit();
		String classname = getIntent().getStringExtra("classname");
		try {
			nextclass = Class.forName(classname.replace("class ", ""));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Intent showSteam = new Intent(SteamEnterAccount.this, nextclass);
		showSteam.putExtra("lang", this.lang);
		SteamEnterAccount.this.startActivity(showSteam);
	}
	
	@Override
	public void onBackPressed() {
/*		Intent showMmSteam = new Intent(SteamEnterAccount.this, Steam.class);
		showMmSteam.putExtra("lang", lang);
		showMmSteam.putExtra("steamid", steamid);
		SteamEnterAccount.this.startActivity(showMmSteam);*/
		Intent showMmSteam = new Intent(SteamEnterAccount.this, MainMenu.class);
		showMmSteam.putExtra("lang", lang);
		SteamEnterAccount.this.startActivity(showMmSteam);
	}

}
