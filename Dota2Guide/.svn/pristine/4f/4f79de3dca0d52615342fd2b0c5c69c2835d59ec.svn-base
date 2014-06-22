package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CrepsAT extends FullScreenActivity {
	private Database database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creps_at);
		String crepsType = getIntent().getStringExtra("crepstype");
		if (crepsType == null) {
			crepsType = "union";
		}

		database = new Database(this);
		try {
			database.openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Cursor grepsATInfo = database.getCrepsInfo(crepsType, lang);
		
		if (crepsType.equals("union")) {
			RelativeLayout crepsBlock1 = (RelativeLayout) findViewById(R.id.crepsBlock1);
			crepsBlock1.setVisibility(View.VISIBLE);
			RelativeLayout crepsBlock2 = (RelativeLayout) findViewById(R.id.crepsBlock2);
			crepsBlock2.setVisibility(View.GONE);
		} else {
			RelativeLayout crepsBlock1 = (RelativeLayout) findViewById(R.id.crepsBlock1);
			crepsBlock1.setVisibility(View.GONE);
			RelativeLayout crepsBlock2 = (RelativeLayout) findViewById(R.id.crepsBlock2);
			crepsBlock2.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView)crepsBlock2.findViewById(R.id.imageCrepsMap);
			String uri = "drawable/" + database.getStringCell(grepsATInfo, "image");
			if (uri.endsWith(".png")) {
				uri = uri.substring(0, uri.length() - 4);
			}
			int imageResource = this.getResources().getIdentifier(uri, null, this.getPackageName());
			imageView.setImageResource(imageResource);
		}
		TextView allienTroopsDesc = (TextView) findViewById(R.id.allienTroopsDesc);
		TextView crepsTitle = (TextView) findViewById(R.id.crepsTitle);
		crepsTitle.setText(database.getStringCell(grepsATInfo, "name"));
		String crepsDescription = database.getStringCell(grepsATInfo, "description");
		allienTroopsDesc.setText(unescape(crepsDescription));
		String crepsParamsStr = database.getStringCell(grepsATInfo, "params");
		
		String[] crepsParamsList = crepsParamsStr.split("[|]");
		
		List<String> crepsParamsListGrid = new ArrayList<String>();
		crepsParamsListGrid.add("");
		crepsParamsListGrid.add(getResources().getString(R.string.gold_killing));
		crepsParamsListGrid.add(getResources().getString(R.string.exp_killing));
		
		for (int i = 0; i < crepsParamsList.length; i++) {
			crepsParamsListGrid.add(crepsParamsList[i]);
		}
		
		// добавление шапки в грид
		
		FixedGridView crepsGridParam = (FixedGridView) findViewById(R.id.gridCrepsParams);
		crepsGridParam.setAdapter(new GridAdapterCrepsParams(CrepsAT.this, crepsParamsListGrid));
		/* ads */
		/* ads */
	}

}
