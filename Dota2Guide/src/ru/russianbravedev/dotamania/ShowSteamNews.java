package ru.russianbravedev.dotamania;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import ru.russianbravedev.dotamania.R;

public class ShowSteamNews extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_steam_news);
		String newstitle = getIntent().getStringExtra("newstitle");
		String newsdate = getIntent().getStringExtra("newsdate");
		String newsauthor = getIntent().getStringExtra("newsauthor");
		String newstext = getIntent().getStringExtra("newstext");
		
		RomulTextView newstitletv = (RomulTextView) findViewById(R.id.newsTitle);
		TextView newsauthortv = (TextView) findViewById(R.id.newsAuthor);
		TextView newsdatetv = (TextView) findViewById(R.id.newsDate);
		TextView newstexttv = (TextView) findViewById(R.id.newsText);
		
		newstitletv.setText(newstitle);
		newsauthortv.setText(newsauthor);
		newstexttv.setText(Html.fromHtml(newstext));
		newsdatetv.setText(newsdate);
	}
}
