package ru.russianbravedev.dotamania;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import ru.russianbravedev.dotamania.R;

public class Mail extends FullScreenActivity {
	private EditText textThemeEdit;
	private EditText textMessageEdit;
	private MainMenuButtons sendMessageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail);
		
        sendMessageButton = (MainMenuButtons) findViewById(R.id.sendMailButton);
        textThemeEdit = (EditText) findViewById(R.id.themeMessage);
        textMessageEdit = (EditText) findViewById(R.id.textMessage);
        sendMessageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String textTheme = textThemeEdit.getText().toString();
				String textMessage = textMessageEdit.getText().toString();
		        Intent i = new Intent(Intent.ACTION_SEND);
		        i.setType("message/rfc822");
		        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"support@redeyesstudio.ru"});
		        i.putExtra(Intent.EXTRA_SUBJECT, textTheme);
		        i.putExtra(Intent.EXTRA_TEXT   , textMessage);
		        try {
		            startActivity(Intent.createChooser(i, "Send mail..."));
		        } catch (android.content.ActivityNotFoundException ex) {
		            Toast.makeText(Mail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		        }
			}
		});
	}
}
