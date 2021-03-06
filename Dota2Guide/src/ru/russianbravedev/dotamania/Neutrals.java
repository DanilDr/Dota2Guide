package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class Neutrals extends FullScreenActivity {
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neutrals);
		String crepstype = getIntent().getStringExtra("crepstype");
		LinearLayout crepsLinkLayout = (LinearLayout) findViewById(R.id.crepsLinkLayout);
		RomulTextView crespMainTitleType = (RomulTextView) findViewById(R.id.crespMainTitleType);
		if (crepstype != null && crepstype.equals("neutrals")) {
			database  = new Database(this);
			try {
				database.openDataBase();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			final Cursor crepsType = database.getCrepsTypes(lang);
			crepsLinkLayout.removeAllViewsInLayout();
			TextView crepsTypeDecr = (TextView) findViewById(R.id.creps_type_description);
			crepsTypeDecr.setText(R.string.creps_neutals_description);
			crespMainTitleType.setText(R.string.neutral_units);
			Resources res = getResources();
			while (crepsType.moveToNext()) {
				RomulTextView crepsElem = new RomulTextView(this);
				crepsElem.setText(database.getStringCell(crepsType, "name"));
				crepsElem.setAdditParam(database.getStringCell(crepsType, "type"));
				crepsElem.setBackgroundColor(getResources().getColor(R.color.mainbuttoncolor));
				crepsElem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				crepsElem.setGravity(Gravity.CENTER);
				crepsElem.setTextColor(Color.WHITE);
				crepsElem.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.romultext_view_size));
				crepsLinkLayout.addView(crepsElem);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
						getResources().getDimensionPixelSize(R.dimen.romultext_view_height)));
				params.setMargins(0,0,0,getResources().getDimensionPixelSize(R.dimen.tacticsmargin));
				crepsElem.setLayoutParams(params);

				
				crepsElem.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent showCreps = new Intent(Neutrals.this, CrepsAT.class);
						showCreps.putExtra("crepstype", ((RomulTextView)v).getAdditParam());
						Neutrals.this.startActivity(showCreps);
					}
				});
			}
		} else {
			RomulTextView crepsAlliedTroops = (RomulTextView) findViewById(R.id.crepsAlliedTroops);
			RomulTextView crepsNeutralUnits = (RomulTextView) findViewById(R.id.crepsNeutralUnits);
			crespMainTitleType.setVisibility(View.GONE);
			crepsAlliedTroops.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent showCreps = new Intent(Neutrals.this, CrepsAT.class);
					showCreps.putExtra("crepstype", "union");
					Neutrals.this.startActivity(showCreps);
				}
			});
			
			crepsNeutralUnits.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent showCreps = new Intent(Neutrals.this, Neutrals.class);
					showCreps.putExtra("crepstype", "neutrals");
					Neutrals.this.startActivity(showCreps);
				}
			});
		}
		/* ads */
		loadAd(this);
		/* ads */
		
	}
}
