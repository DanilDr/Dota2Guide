package ru.russianbravedev.dotamania;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import ru.russianbravedev.dotamania.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FullScreenActivity extends Activity{
	protected String lang;
	protected Long steamid;
	public static final String PREFS_NAME = "Dota2GuidePrefs";
	public SharedPreferences settings;
	public RelativeLayout advblock;
	public static final String TAG = "Dota2Guide";
	protected ProgressDialog progress;
	
	// задание фоновых цветов активных/неактивных кнопок
	protected int[] colors;
	protected int[][] additColors;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	// основные цвета
    	colors = new int[] { getColorInt(R.color.mainbuttoncolor), getColorInt(R.color.mainbuttoncoloract) };
    	
    	// дополнительные цвета
    	additColors = new int[][] { { getColorInt(R.color.buttonstrengthcolor), getColorInt(R.color.buttonstrengthactcolor)},
    			{getColorInt(R.color.buttonagilitycolor), getColorInt(R.color.buttonagilityactcolor)},
    			{ getColorInt(R.color.buttonintellegencycolor), getColorInt(R.color.buttonintellegencyactcolor)}
    	};
    	
		steamid = getIntent().getLongExtra("steamid", 0);

		settings = getSharedPreferences(PREFS_NAME, 0);
		lang = settings.getString("lang", "");
		steamid = settings.getLong("steamid", 0);
		
		Locale locale = new Locale(lang); 
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		this.getApplicationContext().getResources().updateConfiguration(config, null);
		
    }
	
	protected void loadAd(Activity curActivity) {
		LinearLayout adViewFromXml = (LinearLayout) curActivity.findViewById(R.id.mmediaad);
		if (adViewFromXml != null) {
//			AdRegistration.enableLogging(true);
//			AdRegistration.enableTesting(true);

//			adViewFromXml.setListener(this);
//		    try {
//		        AdRegistration.setAppKey(APP_KEY);
//		    } catch (Exception e) {
//		        Log.e(LOG_TAG, "Exception thrown: " + e.toString());
//		        return;
//		    }
//	        AdTargetingOptions adOptions = new AdTargetingOptions();
//	        adViewFromXml.loadAd(adOptions);
		}
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
	
	public String unescape(String description) {
	    return description.replaceAll("\\\\n", "\\\n");
	}
	
	public boolean checkString(String string) {
        if (string == null) return false;
        return string.matches("^-?\\d+$");
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Intent showItems = new Intent(FullScreenActivity.this, ChoiseLang.class);
			FullScreenActivity.this.startActivity(showItems);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	protected String getDota2LangLink() {
		// задание локализации
		Map<String, String> locales = new HashMap<String, String>();
		locales.put("pt_BR", "?l=brazilian");
		locales.put("bg_BG", "?l=bulgarian");
		locales.put("cs_CZ", "?l=czech");
		locales.put("da_DK", "?l=danish");
		locales.put("nl_NL", "?l=dutch");
		locales.put("en_US", "?l=english");
		locales.put("fi_FI", "?l=finnish");
		locales.put("fr_FR", "?l=french");
		locales.put("de_DE", "?l=german");
		locales.put("el_GR", "?l=greek");
		locales.put("hu_HU", "?l=hungarian");
		locales.put("it_IT", "?l=italian");
		locales.put("ja_JP", "?l=japanese");
		locales.put("ko_KR", "?l=koreana");
		locales.put("no_NO", "?l=norwegian");
		locales.put("pl_PL", "?l=polish");
		locales.put("pt_PT", "?l=portuguese");
		locales.put("ro_RO", "?l=romanian");
		locales.put("ru_RU", "?l=russian");
		locales.put("zh_CN", "?l=schinese");
		locales.put("es_ES", "?l=spanish");
		locales.put("sv_SE", "?l=swedish");
		locales.put("th_TH", "?l=thai");
		locales.put("tr_TR", "?l=turkish");
		return locales.get(lang);
	}
	
    protected int getColorInt(int colorId) {
    	return getResources().getColor(colorId);
    }
}
