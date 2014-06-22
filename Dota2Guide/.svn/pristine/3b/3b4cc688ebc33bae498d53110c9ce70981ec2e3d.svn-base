package ru.russianbravedev.dotamania;

import android.content.Intent;
import android.os.Bundle;
import ru.russianbravedev.dotamania.R;

public class ChoiseLang extends FullScreenActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choise_lang);
	}
	
	@Override
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		return;
	}
}
