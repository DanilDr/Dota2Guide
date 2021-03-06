package ru.russianbravedev.dotamania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.russianbravedev.dotamania.R;

public class ChangeLangButton extends RelativeLayout {
	private LayoutInflater inflater;
	private Drawable imageId;
	private String titleNameId;
	private String localeName;
	private static final String PREFS_NAME = "Dota2GuidePrefs";
	
	public ChangeLangButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		final Context thiscontext = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.changelanguage, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[] {R.attr.image, R.attr.localename, R.attr.titlename});
        imageId = ta.getDrawable(0);
        localeName = ta.getString(1);
        titleNameId = ta.getString(2);
        ta.recycle();
        // установка изображения
        ImageView langFlagImg = (ImageView) findViewById(R.id.langFlagImg);
        langFlagImg.setImageDrawable(imageId);
        // установка названия изображения
        TextView langName = (TextView) findViewById(R.id.langName);
        langName.setText(titleNameId);
        setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences thissettings = ((FullScreenActivity) thiscontext).settings;
				final SharedPreferences.Editor editor = thissettings.edit();
				thissettings = thiscontext.getSharedPreferences(PREFS_NAME, 0);
				editor.putString("lang", localeName);
				editor.commit();
				Intent choiseLang = new Intent(thiscontext, MainMenu.class);
				thiscontext.startActivity(choiseLang);				
			}
		});
	}

}
