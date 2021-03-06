package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowStoreItem extends FullScreenActivity {
	private String itemName;
	private Database database;
	private Cursor storeItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_show_store_item);
		database = new Database(this);
		try {
			database.openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		itemName = getIntent().getStringExtra("itemname");
		storeItem = database.getStoreItem(itemName, lang);

		setTextField(R.id.storeItemName, database.getStringCell(storeItem, "name"), false);
		
		ImageView itemImageIV = (ImageView) findViewById(R.id.storeItemImageLarge);
		
		try {
			itemImageIV.setImageBitmap(new DownloadImageTask().execute(database.getStringCell(storeItem, "image_url_large")).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		setTextField(R.id.storeItemDescription, database.getStringCell(storeItem, "item_description"), false);
	}
	
	private void setTextField(int TVid, String Tvtext, boolean boolType) {
		TextView curTV = (TextView) findViewById(TVid);
		String outText = "";
		if (boolType) {
			if (Tvtext == "false") {
				outText = getString(R.string.no);
			} else {
				outText = getString(R.string.yes);
			}
		} else {
			outText = Tvtext;
		}
		curTV.setText(outText);
		return;
	}

	@Override
	public void onBackPressed() {
		Intent showMm = new Intent(ShowStoreItem.this, Steam.class);
		showMm.putExtra("lang", lang);
		showMm.putExtra("steamid", steamid);
		ShowStoreItem.this.startActivity(showMm);
	}
}
