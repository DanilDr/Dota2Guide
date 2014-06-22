package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class Tactics extends FullScreenActivity {
	private Database database;
	LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tactics);
		View tacticsListLay = (View) findViewById(R.id.tacticsList);
		database = new Database(this);
		
		try {
			database.openDataBase();
		} catch (SQLException sqle) {
			throw new Error("Unable to open database");
		}
		Cursor tacticsList = database.getTactics(lang);
		Resources res = getResources();
		while (tacticsList.moveToNext()) {
			RomulTextView tacticElem = new RomulTextView(this);
			tacticElem.setText(database.getStringCell(tacticsList, "name"));
			tacticElem.setId(tacticsList.getPosition());
			tacticElem.setBackgroundColor(getResources().getColor(R.color.mainbuttoncolor));
			tacticElem.setGravity(Gravity.CENTER);
			tacticElem.setTextColor(Color.WHITE);
			((LinearLayout) tacticsListLay).addView(tacticElem);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					getResources().getDimensionPixelSize(R.dimen.romultext_view_height)));
			params.setMargins(0,0,0,getResources().getDimensionPixelSize(R.dimen.tacticsmargin));
			tacticElem.setLayoutParams(params);
			tacticElem.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.romultext_view_size));
			tacticElem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String tacticName = (String) ((TextView)v).getText();
					Intent showTactic = new Intent(Tactics.this, TacticsInfo.class);
					showTactic.putExtra("tactic", tacticName);
					Tactics.this.startActivity(showTactic);
				}
			});
		}
		/* ads */
		/* ads */

	}

}
