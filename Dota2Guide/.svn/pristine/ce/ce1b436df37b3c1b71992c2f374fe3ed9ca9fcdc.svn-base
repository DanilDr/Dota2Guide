package ru.russianbravedev.dotamania;

import java.util.Map;
import java.util.TreeMap;
import ru.russianbravedev.dotamania.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenu extends FullScreenActivity {

	Map<Integer, Class<? extends Activity>> mmButtonsList = new TreeMap<Integer, Class<? extends Activity>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		MainMenuButtons likeMMButton = (MainMenuButtons) findViewById(R.id.mainMenuLike);
		likeMMButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(getResources().getString(R.string.link_market)));
				startActivity(intent);
			}
		});
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
