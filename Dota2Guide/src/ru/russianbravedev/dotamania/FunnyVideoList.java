package ru.russianbravedev.dotamania;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import ru.russianbravedev.dotamania.R;

public class FunnyVideoList extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_funny_video_list);
		String[] inputListVideos = getIntent().getStringArrayExtra("listvideos");
		LinearLayout listVideosContainer = (LinearLayout) findViewById(R.id.listVideosContainer);
		Resources res = getResources();
		for (int i = 0; i < inputListVideos.length; i++) {
			final String[] curNameLink = inputListVideos[i].split("[@]");
			RomulTextView funnyVideoLink = new RomulTextView(this);
			funnyVideoLink.setBackgroundColor(getColorInt(R.color.mainbuttoncolor));
			funnyVideoLink.setText(curNameLink[0]);
			funnyVideoLink.setTextColor(Color.WHITE);
			funnyVideoLink.setGravity(Gravity.CENTER_VERTICAL);
			listVideosContainer.addView(funnyVideoLink);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					getResources().getDimensionPixelSize(R.dimen.romultext_view_height)));
			params.setMargins(0,0,0,getResources().getDimensionPixelSize(R.dimen.tacticsmargin));
			funnyVideoLink.setLayoutParams(params);
			
			funnyVideoLink.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + curNameLink[1])));
				}
			});
			funnyVideoLink.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.romultext_view_size));
			
		}
		/* ads */
		/* ads */
	}
}
