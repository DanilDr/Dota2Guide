package ru.russianbravedev.dotamania;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ru.russianbravedev.dotamania.R;

public class Info extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		TextView studioName = (TextView) findViewById(R.id.studioName);
		TextView studioEmail = (TextView) findViewById(R.id.studioEmail);
		TextView choiseLanguageTV = (TextView) findViewById(R.id.choiseLanguage);
		
		studioName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setData(Uri.parse("http://www.redeyesstudio.ru/"));
//				startActivity(intent);
			}
		});
		
		studioEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent showMmElement = new Intent(Info.this, Mail.class);
				showMmElement.putExtra("lang", lang);
				Info.this.startActivity(showMmElement);
			}
		});
		
		choiseLanguageTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent showChangeLang = new Intent(Info.this, ChoiseLang.class);
				Info.this.startActivity(showChangeLang);
			}
		});
		
	}
}
