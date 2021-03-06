package ru.russianbravedev.dotamania;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import ru.russianbravedev.dotamania.R;

public class MainMenuButtons extends RelativeLayout {
	
	private LayoutInflater inflater;
	private Context context;
	private String titlemainmenu;
	private String iconmainmenu;
	private String activityName;

	public MainMenuButtons(final Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mainmenubuttons, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[] {R.attr.titlemm, R.attr.iconmm, R.attr.activity});
        titlemainmenu = ta.getString(0);
        iconmainmenu = ta.getString(1);
        activityName = ta.getString(2);
        final FullScreenActivity activityParent = (FullScreenActivity) context;
		
        if (activityName != null) {
	        setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
		        	try {
						Class<?> activityClass = Class.forName(context.getPackageName() + '.' + activityName);
						Intent showMmElement = new Intent(getContext(), activityClass);
						showMmElement.putExtra("lang", activityParent.lang);
						activityParent.startActivity(showMmElement);				
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
        }
        setTitle(titlemainmenu);
        setIcon(iconmainmenu);
	}
	
	private void setTitle(String titleStr) {
		RomulTextView mainTitle = (RomulTextView) findViewById(R.id.mainMenuTitleText);
		if (titleStr != null) {
			int titleres = context.getResources().getIdentifier(titleStr.split("[.]")[2], "string", context.getPackageName());
			mainTitle.setText(titleres);
		} else {
			mainTitle.setVisibility(View.GONE);
		}
	  }
	
	private void setIcon(String iconStr) {
		if (iconStr != null) {
			ImageView mainTitleIcon = (ImageView) findViewById(R.id.mainMenuImage);
			int titleiconres = context.getResources().getIdentifier(iconStr.split("[.]")[2], "drawable", context.getPackageName());
			mainTitleIcon.setImageResource(titleiconres);
		}
		return;
	}
}
