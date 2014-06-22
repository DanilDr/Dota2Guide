package ru.russianbravedev.dotamania;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ru.russianbravedev.dotamania.R;

public class Accordion extends FrameLayout {
	
	private LayoutInflater inflater;
	private Context context;
	private RelativeLayout mainContent;
	private final String dataparam;
	
	public Accordion(Context context) {
		this(context, null);
	}
	
	public Accordion(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_accordion, this, true);
        
		mainContent = (RelativeLayout) findViewById(R.id.accordMainContent);
		
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[] {R.attr.titleaccordion, R.attr.iconaccordion,
        		R.attr.datatype, R.attr.dataparam }, 0, 0);
        String titleaccordion = ta.getString(0);
        String titleiconaccordion = ta.getString(1);
        dataparam = ta.getString(3);
        

        setTitleText(titleaccordion);
        setTitleIcon(titleiconaccordion);
        
        LinearLayout layoutShowHide = (LinearLayout) findViewById(R.id.accordShowHide);
        
        if (dataparam != null && dataparam.equals("nohide")) {
        	mainContent.setVisibility(View.VISIBLE);
        } else {
	        layoutShowHide.setOnClickListener(new OnClickListener() {
	        	
				@Override
				public void onClick(View v) {
					if (mainContent.getVisibility() == View.GONE) {
						mainContent.setVisibility(View.VISIBLE);
					} else {
						mainContent.setVisibility(View.GONE);
					}
				}
			});
        }
	}
	
	private void setTitleText(String titleStr) {
		if (titleStr != null) {
			RomulTextView mainTitle = (RomulTextView) findViewById(R.id.accordTitle);
			int titleres = context.getResources().getIdentifier(titleStr.split("[.]")[2], "string", context.getPackageName());
			mainTitle.setText(titleres);
		}
		return;
	}
	
	private void setTitleIcon(String iconStr) {
		ImageView mainTitleIcon = (ImageView) findViewById(R.id.accordTopIcon);
		if (iconStr != null) {
			int titleiconres = context.getResources().getIdentifier(iconStr.split("[.]")[2], "drawable", context.getPackageName());
			mainTitleIcon.setImageResource(titleiconres);
			return;
		} else {
			mainTitleIcon.setVisibility(View.GONE);
		}
	}
}
