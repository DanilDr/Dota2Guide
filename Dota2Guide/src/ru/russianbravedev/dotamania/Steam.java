package ru.russianbravedev.dotamania;

import android.os.Bundle;
import android.content.Intent;
import ru.russianbravedev.dotamania.R;

public class Steam extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steam);
	}
	@Override
	public void onBackPressed() {
		Intent showMm = new Intent(Steam.this, Services.class);
		showMm.putExtra("lang", lang);
		Steam.this.startActivity(showMm);
	}
}
