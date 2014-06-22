package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;

public class SteamNews extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steam_news);
		
		FixedGridView steamNewsGrid = (FixedGridView) findViewById(R.id.gridSteamNews);
		GetSteamNews getSteamNews = new GetSteamNews(this, lang);
		try {
			getSteamNews.execute(steamNewsGrid).get(20000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		};
	}
}
