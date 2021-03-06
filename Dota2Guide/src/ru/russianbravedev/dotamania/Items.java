package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

public class Items extends FullScreenActivity {
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		database = new Database(this);
		
		try {
			database.openDataBase();
		} catch (SQLException sqle) {
			throw new Error("Unable to open database");
		}
		
		// Мапировка категорий предметов
		SparseArray<String> itemsType = new SparseArray<String>();
		itemsType.append(R.id.accorconsumables, "consumables");
		itemsType.append(R.id.accorattributes, "attributes");
		itemsType.append(R.id.accorarmaments, "armaments");
		itemsType.append(R.id.accorarcane, "arcane");
		itemsType.append(R.id.accorcommon, "common");
		itemsType.append(R.id.accorsupport, "support");
		itemsType.append(R.id.accorcaster, "caster");
		itemsType.append(R.id.accorweapons, "weapons");
		itemsType.append(R.id.accorarmor, "armor");
		itemsType.append(R.id.accorartifacts, "artifacts");
		itemsType.append(R.id.accorsecret, "secret");
		
		OnItemClickListener gridItemClick =  new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				TextView itemname = (TextView) arg1.findViewById(R.id.itemConsistName); 
				Intent showItem = new Intent(Items.this, ShowItem.class);
				showItem.putExtra("lang", lang);
				showItem.putExtra("itemname", itemname.getText());
				Items.this.startActivity(showItem);
				
			}
		};
		
		for (int i = 0; i < itemsType.size(); i++) {
			Integer itemAccorID = itemsType.keyAt(i);
			String typeName = itemsType.get(itemAccorID);
			Accordion curItemsAccor = (Accordion) findViewById(itemAccorID);
			FixedGridView itemsGrid = (FixedGridView) curItemsAccor.findViewById(R.id.accordGrid);
			itemsGrid.setAdapter(new GridAdapterItems(this, database.getListItems(typeName, lang)));
			itemsGrid.setOnItemClickListener(gridItemClick);
		}

		/* ads */
		loadAd(this);
		/* ads */
		
	}
	
	@Override
	public void onBackPressed() {
		Intent showMm = new Intent(Items.this, MainMenu.class);
		Items.this.startActivity(showMm);
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
}