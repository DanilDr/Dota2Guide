package ru.russianbravedev.dotamania;

import java.io.IOException;

import ru.russianbravedev.dotamania.ChoiseLang;
import ru.russianbravedev.dotamania.Database;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Start extends FullScreenActivity {
	private Handler mHandler = new Handler();
	private Class<? extends FullScreenActivity> nextclass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Runnable runmhandler;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		ImageView startlogo = (ImageView) findViewById(R.id.startlogo);
		
		Database database = new Database(this);
		try {
			database.checkDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (lang == "") {
			nextclass = ChoiseLang.class;
		} else {
			nextclass = MainMenu.class;
		}

		runmhandler = new Runnable() {
			
			@Override
			public void run() {
				Intent choiseLang = new Intent(Start.this, nextclass);
				Start.this.startActivity(choiseLang);				
			}
		};
		
		mHandler.postDelayed(runmhandler, 3000);

		OnClickListener mainlolclk = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(runmhandler);
				Intent choiseLang = new Intent(Start.this, nextclass);
				Start.this.startActivity(choiseLang);				
			}
		};
		startlogo.setOnClickListener(mainlolclk);
		
	}
}
