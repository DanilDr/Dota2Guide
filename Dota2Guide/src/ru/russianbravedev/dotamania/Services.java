package ru.russianbravedev.dotamania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import ru.russianbravedev.dotamania.R;

public class Services extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("steamid", 0);
		editor.commit();
		
	}
	
	@Override
	public void onBackPressed() {
		Intent showMm = new Intent(Services.this, MainMenu.class);
		showMm.putExtra("lang", lang);
		Services.this.startActivity(showMm);
	}
}
