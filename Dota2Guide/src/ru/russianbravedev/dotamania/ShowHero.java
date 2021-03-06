package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import ru.russianbravedev.dotamania.R;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("SetJavaScriptEnabled")
public class ShowHero extends FullScreenActivity implements OnEditorActionListener {


	private Database database = null;
	private Cursor heroInfoCur;
	private String inheroname;
	private int herotypeid;
	private RomulTextView[] btnsHero;
	private int backcolor;
	private int[] backOnOff;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_hero);
		database = new Database(this);
		final View heroInfo = new View(this);
		final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inheroname = getIntent().getStringExtra("heroname");
		herotypeid = getIntent().getIntExtra("herotypeid", 0);
		btnsHero = new RomulTextView[]{(RomulTextView) findViewById(R.id.btnHeroBio),
				(RomulTextView) findViewById(R.id.btnHeroSkills), (RomulTextView) findViewById(R.id.btnHeroGuid)};
		final LinearLayout layHeroMain = (LinearLayout) findViewById(R.id.mainHeroInfo);
		
		try {
			heroInfoCur = database.getHeroByName(inheroname, lang);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnsHero[0].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showHeroBio(heroInfo, inflater, layHeroMain);
				return;
			}
		});
		
		btnsHero[1].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showHeroSkills(heroInfo, inflater, layHeroMain);
				return;
			}
		});

		btnsHero[2].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showHeroGuides(heroInfo, inflater, layHeroMain);
				return;
			}
		});
		
		/* Задание параметров в зависимости от типа героя */
		backOnOff = additColors[herotypeid];
		
		backcolor = backOnOff[0];
		
		btnsHero[0].performClick();
		
		/* ads */
		loadAd(this);
		/* ads */
		
        return;
	}
    
	// показ данных о герое
	private void showHeroBio(View heroInfo, LayoutInflater inflater, LinearLayout layHeroMain) {
		btnsHero[0].setBackgroundColor(backOnOff[1]);
		btnsHero[1].setBackgroundColor(backOnOff[0]);
		btnsHero[2].setBackgroundColor(backOnOff[0]);
		layHeroMain.removeAllViewsInLayout();

		heroInfo = inflater.inflate(R.layout.herobio, layHeroMain);
		
		SparseArray<String> bioTexts = new SparseArray<String>();
		bioTexts.put(R.id.bioHeroName, "name");
		bioTexts.put(R.id.bioHeroStatus, "status");
		bioTexts.put(R.id.bioHeroDescription, "description");
		
		for(int i = 0; i < bioTexts.size(); i++) {
			Integer key = bioTexts.keyAt(i);
			TextView textBioTV = (TextView) heroInfo.findViewById(key);
			textBioTV.setText(database.getStringCell(heroInfoCur, bioTexts.get(key)));
		}
		
		final ImageView imgHero = (ImageView) heroInfo.findViewById(R.id.bioHeroBigImages);
		String uri = "drawable/" + database.getStringCell(heroInfoCur, "image");
		if (uri.endsWith(".png")) {
			uri = uri.substring(0, uri.length() - 4);
		}
		int imageResource = this.getResources().getIdentifier(uri, null, this.getPackageName());
		imgHero.setImageResource(imageResource);
		imgHero.setBackgroundColor(backcolor);
		
		return;
	}
	
	private void showHeroSkills(View heroInfo, LayoutInflater inflater, LinearLayout layHeroMain) {
		String[] heroParams;
		View agilityLayout;
		Cursor heroAgilities = null;
		btnsHero[0].setBackgroundColor(backOnOff[0]);
		btnsHero[1].setBackgroundColor(backOnOff[1]);
		btnsHero[2].setBackgroundColor(backOnOff[0]);
		layHeroMain.removeAllViewsInLayout();

		heroInfo = inflater.inflate(R.layout.heroskills, layHeroMain);
		
		TextView tvheroAbilityName;
		TextView tvheroAbilityDescription;
		int imageResource;
		ImageView tvheroAbilityImage;
		TextView tvheroAbilityMana;
		TextView tvheroAbilityCooldown;
		View heroAgilityParent;
		TextView tvheroAbilityParams;
		TextView tvheroAbilityNote;
		TextView tvheroAbilityAganim;
		
		LinearLayout layheroblock1;
		
		LinearLayout layHeroAbilities = (LinearLayout) heroInfo.findViewById(R.id.mainHeroAbilities);
		LayoutInflater factory = LayoutInflater.from(this);
		
		RelativeLayout heroblock1 = (RelativeLayout) heroInfo.findViewById(R.id.heroAbilityBlock1);
		heroblock1.setBackgroundColor(backcolor);
		RelativeLayout heroblock2 = (RelativeLayout) heroInfo.findViewById(R.id.heroAbilityBlock2);
		heroblock2.setBackgroundColor(backcolor);
		

		/* вывод начальных параметров */
		heroParams = database.getStringCell(heroInfoCur, "params").split("[|]");
		SparseIntArray heroStartParams = new SparseIntArray();
		heroStartParams.put(R.id.valueHeroStrenght, 0);
		heroStartParams.put(R.id.valueHeroAgility, 1);
		heroStartParams.put(R.id.valueHeroIntelligence, 2);
		heroStartParams.put(R.id.valueHeroDamage, 3);
		heroStartParams.put(R.id.valueHeroArmor, 5);
		heroStartParams.put(R.id.valueHeroMovespeed, 4);
		
		for (int i = 0; i < heroStartParams.size(); i++) {
			Integer key = heroStartParams.keyAt(i);
			TextView startParamsTV = (TextView) heroInfo.findViewById(key);
			startParamsTV.setText(heroParams[heroStartParams.get(key)]);
		}
		/*! вывод начальных параметров !*/
		
		/* вывод способностей героя */
		try {
			heroAgilities = database.getHeroAbilitiesByName(this.inheroname, lang);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (heroAgilities.moveToFirst(); !heroAgilities.isAfterLast(); heroAgilities.moveToNext()) {
			agilityLayout = factory.inflate(R.layout.hero_abilities, null);
			layheroblock1 = (LinearLayout) agilityLayout.findViewById(R.id.heroabBlock1);
			layheroblock1.setBackgroundColor(backcolor);
			layHeroAbilities.addView(agilityLayout);
			tvheroAbilityName = (TextView) agilityLayout.findViewById(R.id.heroAbilityName);
			tvheroAbilityName.setText(database.getStringCell(heroAgilities, "name"));
			tvheroAbilityDescription = (TextView) agilityLayout.findViewById(R.id.heroAbilityDescription);
			tvheroAbilityDescription.setText(database.getStringCell(heroAgilities, "description"));
			tvheroAbilityImage = (ImageView)agilityLayout.findViewById(R.id.heroAbilityImage);
			String uri = "drawable/" + database.getStringCell(heroAgilities, "image");
			if (uri.endsWith(".png")) {
				uri = uri.substring(0, uri.length() - 4);
			}
			imageResource = ShowHero.this.getResources().getIdentifier(uri, null, ShowHero.this.getPackageName());
			tvheroAbilityImage.setImageResource(imageResource);
			// ����� ����
			String heroAbilityMana = database.getStringCell(heroAgilities, "mana");
			tvheroAbilityMana = (TextView) agilityLayout.findViewById(R.id.heroAbilityMana);
			if (heroAbilityMana != null && heroAbilityMana.length() != 0) {
				tvheroAbilityMana.setText(heroAbilityMana);
			} else {
				heroAgilityParent = (View) tvheroAbilityMana.getParent();
				heroAgilityParent.setVisibility(View.GONE);
			}
			// ����� ��������
			String heroAbilityCooldown = database.getStringCell(heroAgilities, "cooldown");
			tvheroAbilityCooldown = (TextView) agilityLayout.findViewById(R.id.heroAbilityCooldown);
			if (heroAbilityCooldown != null && heroAbilityCooldown.length() != 0) {
				tvheroAbilityCooldown.setText(heroAbilityCooldown);
			} else {
				heroAgilityParent = (View) tvheroAbilityCooldown.getParent();
				heroAgilityParent.setVisibility(View.GONE);
			}
			// ��������� �����������
			String heroAbilityParams = database.getStringCell(heroAgilities, "params");
			String[] heroAbilityParamsList = heroAbilityParams.split("[|]");
			tvheroAbilityParams = (TextView) agilityLayout.findViewById(R.id.heroAgilityParams);
			tvheroAbilityParams.setText((String) TextUtils.join("\n", heroAbilityParamsList));
			// ��������
			String heroAbilityNote = database.getStringCell(heroAgilities, "note");
			tvheroAbilityNote = (TextView) agilityLayout.findViewById(R.id.heroAbilityNote);
			tvheroAbilityNote.setText(heroAbilityNote);
			// �� ���������
			String heroAbilityAganim = database.getStringCell(heroAgilities, "aganim");
			tvheroAbilityAganim = (TextView) agilityLayout.findViewById(R.id.heroAbilityAganim);
			if (heroAbilityAganim != null && heroAbilityAganim.length() > 0) {
				tvheroAbilityAganim.setText(heroAbilityAganim);
			} else {
				tvheroAbilityAganim.setVisibility(View.GONE);
			}
			
			// кнопка просмотра видео
			RomulTextView btnHeroShowSkillVideo = (RomulTextView) agilityLayout.findViewById(R.id.btnHeroShowSkillVideo);
			// ссылка на youtube
			final String youtubeLink = database.getStringCell(heroAgilities, "youtube");
			if (youtubeLink != null && youtubeLink != "") {
				btnHeroShowSkillVideo.setBackgroundColor(backOnOff[0]);
				btnHeroShowSkillVideo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeLink)));
					}
				});				
			} else {
				btnHeroShowSkillVideo.setVisibility(View.GONE);
			}
			
		}
		/*! вывод способностей героя !*/
		
		/* ����� ������� ����������� ��������, ����, ����� � ������ �� ������ */
		String[] heroLevelParams = database.getStringCell(heroInfoCur, "levelparams").split("[|]");
		int[] heroLevParViewID = {R.id.heroALH1, R.id.heroALH16, R.id.heroALH25, 
				R.id.heroALM1, R.id.heroALM16, R.id.heroALM25, 
				R.id.heroALD1, R.id.heroALD16, R.id.heroALD25, 
				R.id.heroALA1, R.id.heroALA16, R.id.heroALA25 };
		for (int i = 0; i < heroLevParViewID.length; i++) {
			TextView heroLevelParamView = (TextView) heroInfo.findViewById(heroLevParViewID[i]);
			heroLevelParamView.setText(heroLevelParams[i]);
		}
		/*! ����� ������� ����������� ��������, ����, ����� � ������ �� ������ !*/
		
		/* ����� ������ ����/���� */
		String heroViewDN = database.getStringCell(heroInfoCur, "viewdaynight");
		TextView heroViewDBView = (TextView) heroInfo.findViewById(R.id.herodistview);
		heroViewDBView.setText(heroViewDN);
		/*! ����� ������ ����/���� !*/
		
		/* ����� ���������� ����� */
		String[] heroAttackParam = database.getStringCell(heroInfoCur, "attackparam").split("[|]");
		TextView[] heroAttackParamsView = {(TextView) heroInfo.findViewById(R.id.herodistattack), 
				(TextView) heroInfo.findViewById(R.id.herospeedattack)};
		heroAttackParamsView[0].setText(heroAttackParam[0]);
		heroAttackParamsView[1].setText(heroAttackParam[1]);
		/*! ����� ���������� ����� !*/
		
		return;
	}
	
	private void showHeroGuides(View heroInfo, LayoutInflater inflater, LinearLayout layHeroMain) {
		Cursor heroGuides = null; // курсор гайдов
		Cursor heroItems = null; // курсор используемых героем предметов
		btnsHero[0].setBackgroundColor(backOnOff[0]);
		btnsHero[1].setBackgroundColor(backOnOff[0]);
		btnsHero[2].setBackgroundColor(backOnOff[1]);
		layHeroMain.removeAllViewsInLayout();
		int heroguidelay = R.layout.hero_guide;
		heroInfo = inflater.inflate(heroguidelay, layHeroMain);
		
		// список соответствия названия в БД и виджета отображения данных
		Map<String, View> heroGuidesMap = new HashMap<String, View>();
		heroGuidesMap.put("about", (View) findViewById(R.id.accordheroabout));
		//heroGuidesMap.put("strateg", (View) findViewById(R.id.accordherohowtoplay));
		heroGuidesMap.put("advice", (View) findViewById(R.id.accordheroadvice));
		heroGuidesMap.put("facts", (View) findViewById(R.id.accordherofacts));
		// запрос к БД
		try {
			heroGuides = database.getHeroGuidesByName(this.inheroname, lang);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// цикл заполнения О герое, Советы и Факты
		for (Map.Entry<String, View> entry : heroGuidesMap.entrySet()) {
			String guideName = entry.getKey();
			View guideView = entry.getValue();
			guideView.findViewById(R.id.accordShowHide).setBackgroundColor(backOnOff[0]);
			String geroGuideText = database.getStringCell(heroGuides, guideName);
			if (geroGuideText != null && geroGuideText.length() > 0) {
				TextView curGuide = (TextView) guideView.findViewById(R.id.accordText);
				RelativeLayout accordMainContent = (RelativeLayout) guideView.findViewById(R.id.accordMainContent);
				accordMainContent.setBackgroundColor(backcolor);
				guideView.setVisibility(View.VISIBLE);
				curGuide.setText(unescape(geroGuideText));
			} else {
				
			}
		}
		// вывод информации о прокачке герои в веб страницу (китайская версия)
		String urlPage = database.getStringCell(heroGuides, "urlpage");
		if (urlPage != null && urlPage.length() > 0) {
			View accordheropage = (View) findViewById(R.id.accordheropage);
			accordheropage.setVisibility(View.VISIBLE);
			accordheropage.findViewById(R.id.accordShowHide).setBackgroundColor(backOnOff[0]); // задание цвета шапки раскрывающегося меню
			accordheropage.findViewById(R.id.accordMainContent).setBackgroundColor(backcolor); // задание цвета рамки
			LinearLayout heroGuidePageContent = (LinearLayout) accordheropage.findViewById(R.id.accordMainLinearLayout);
			heroGuidePageContent.removeAllViewsInLayout();
			WebView heroGuidePage = new WebView(this);
			heroGuidePageContent.addView(heroGuidePage);
			heroGuidePage.getSettings().setLoadWithOverviewMode(true);
			heroGuidePage.getSettings().setUseWideViewPort(true);
			Resources res = getResources();
			heroGuidePage.getLayoutParams().height = res.getDimensionPixelSize(R.dimen.urlpageheight);
			heroGuidePage.loadUrl(urlPage);
			heroGuidePage.getSettings().setJavaScriptEnabled(true);
			heroGuidePage.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
			heroGuidePage.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
				    return false;
				}
				
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					view.setVisibility(View.GONE);
				}
				
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					view.loadUrl("javascript:document.getElementsByTagName('head')[0].innerHTML='<style>" +
						"body * { margin:0 !important; }" +
						"#wanmei_top, #dota2_topnav, .item_left, .video_wrap, #bar_nav, .item_top, #footerIframe { display: none !important; }" +
						".item_right, .w1002 { float: left !important; width: auto; margin: 0px !important;}" +
						"</style>" +
						"<meta name=\"viewport\" content=\"width=480, initial-scale=0.5, maximum-scale=0.5, user-scalable=no\" />' + document.getElementsByTagName('head')[0].innerHTML;");
					view.setVisibility(View.VISIBLE);
				}
			});
		}
		// вывод информации о Испольуемых предметах
		SparseIntArray heroItemsMap = new SparseIntArray();
		// startitems, firstitems, mainitems, caseitems
		heroItemsMap.put(1, R.string.startitems);
		heroItemsMap.put(2, R.string.firstitems);
		heroItemsMap.put(3, R.string.mainitems);
		heroItemsMap.put(4, R.string.caseitems);
		View heroItemsMainView = (View) findViewById(R.id.accordheroitems); // скравающееся меню
		heroItemsMainView.findViewById(R.id.accordShowHide).setBackgroundColor(backOnOff[0]); // задание цвета шапки раскрывающегося меню
		heroItemsMainView.findViewById(R.id.accordMainContent).setBackgroundColor(backcolor); // задание цвета рамки
		LinearLayout heroItemsContent = (LinearLayout) heroItemsMainView.findViewById(R.id.accordMainLinearLayout);
		heroItemsContent.removeAllViewsInLayout();

		OnItemClickListener gridItemClick =  new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				TextView itemname = (TextView) arg1.findViewById(R.id.itemConsistName); 
				Intent showItem = new Intent(ShowHero.this, ShowItem.class);
				showItem.putExtra("lang", lang);
				showItem.putExtra("itemname", itemname.getText());
				showItem.putExtra("returnback", true);// возврат назад
				ShowHero.this.startActivity(showItem);
				
			}
		};

		
		for (int i = 0; i < heroItemsMap.size(); i++) {
			Integer typeId = heroItemsMap.keyAt(i);
			Integer typeName = heroItemsMap.get(typeId);
			try {
				heroItems = database.getHeroGuidesItems(this.inheroname, lang, typeId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (heroItems != null) {
				View heroUserdItemsView;
				LayoutInflater li = LayoutInflater.from(this);
				heroUserdItemsView = li.inflate(R.layout.herouseditems, null);
				TextView curNameGroupItems = (TextView) heroUserdItemsView.findViewById(R.id.textHeroUsedItems);
				curNameGroupItems.setText(typeName);
				FixedGridView gridHeroUsedItems = (FixedGridView) heroUserdItemsView.findViewById(R.id.gridHeroUsedItems);
				gridHeroUsedItems.setAdapter(new GridAdapterItems(ShowHero.this, heroItems));
				heroItemsContent.addView(heroUserdItemsView);
				gridHeroUsedItems.setOnItemClickListener(gridItemClick);
			}
		}
		return;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		return false;
	}

}
