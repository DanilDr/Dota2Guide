package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TacticsInfo extends FullScreenActivity {

	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tactics_info);
		View listInfoTactic = (View) findViewById(R.id.listInfoTactic);
		database = new Database(this);
		try {
			database.openDataBase();
		} catch (SQLException sqle) {
			throw new Error("Unable to open database");
		}
		String intacticname = getIntent().getStringExtra("tactic");
		RomulTextView titleTactic = (RomulTextView) findViewById(R.id.titleTactic);
		titleTactic.setTextColor(Color.WHITE);
		titleTactic.setText(intacticname);
		Cursor tacticsList = database.getInfoTactic(intacticname, lang);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(20, 10, 20, 10);
		llp.gravity = Gravity.CENTER_VERTICAL;
		while (tacticsList.moveToNext()) {
			String accTacticName = database.getStringCell(tacticsList, "name");
			Accordion tacticsAccor = new Accordion(this, null);
			RomulTextView titleInfoTactic = (RomulTextView) tacticsAccor.findViewById(R.id.accordTitle);
			titleInfoTactic.setText(accTacticName);
			titleInfoTactic.setLayoutParams(llp);
			((TextView) titleInfoTactic).setLineSpacing(Float.parseFloat("1.2"), Float.parseFloat("1.1"));
			TextView tacticInfoDescr = (TextView) tacticsAccor.findViewById(R.id.accordText);
			tacticInfoDescr.setText(unescape(database.getStringCell(tacticsList, "description")));
			((LinearLayout) listInfoTactic).addView(tacticsAccor);
			
		}
		/* ads */
		loadAd(this);
		/* ads */
	}
}
