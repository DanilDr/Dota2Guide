package ru.russianbravedev.dotamania;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import ru.russianbravedev.dotamania.R;

public class RomulTextView extends TextView {
	private String additParam;

	public RomulTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initfont();
	}

	public RomulTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initfont();
	}

	public RomulTextView(Context context) {
		super(context);
		initfont();
	}
	
	@SuppressLint("DefaultLocale")
	private void initfont() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Romul.otf");
		setTypeface(tf, 1);
		String curtext = (String) this.getText();
		this.setText(curtext.toUpperCase());
		Resources res = getResources();
		int romulTextViewH = res.getDimensionPixelSize(R.dimen.romultext_view_height);
		this.setHeight(romulTextViewH);
	}
	public void setAdditParam(String inparam){
		additParam = inparam;
		return;
	}
	
	public String getAdditParam() {
		return this.additParam;
	}
}
