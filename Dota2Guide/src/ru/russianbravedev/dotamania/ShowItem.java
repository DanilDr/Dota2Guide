package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowItem extends FullScreenActivity {
	
	private String inItemName;
	private Accordion mainItem;
	private View itemInfo;
	private int itemInfoID;
	private boolean returnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Cursor itemInfoCur = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_item);
		Database database = new Database(this);
		final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mainItem = (Accordion) findViewById(R.id.accorditem);
		inItemName = getIntent().getStringExtra("itemname");
		returnBack = getIntent().getBooleanExtra("returnback", false);
		TextView itemTitle = (TextView) mainItem.findViewById(R.id.accordTitle);
		itemTitle.setText(inItemName);
		ImageView accorIcon = (ImageView) mainItem.findViewById(R.id.accordTopIcon);
		accorIcon.setVisibility(View.GONE);
		RelativeLayout mainContent = (RelativeLayout) mainItem.findViewById(R.id.accordMainContent);
		mainContent.removeAllViewsInLayout();
		itemInfo = inflater.inflate(R.layout.item_info, mainContent);
		try {
			database.openDataBase();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			itemInfoCur = database.getItemByName(inItemName, lang);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		itemInfoID = Integer.parseInt(database.getStringCell(itemInfoCur, "_id"));
		ImageView itemImage =(ImageView) mainItem.findViewById(R.id.itemImage);
		String uri = "drawable/" + database.getStringCell(itemInfoCur, "image");
		if (uri.endsWith(".png")) {
			uri = uri.substring(0, uri.length() - 4);
		}
		int imageResource = ShowItem.this.getResources().getIdentifier(uri, null, ShowItem.this.getPackageName());
		itemImage.setImageResource(imageResource);
		SparseArray<String> tm = new SparseArray<String>();
		tm.put(R.id.itemCost, "cost");
		tm.put(R.id.itemDescription1, "description");
		tm.put(R.id.itemDescription2, "description2");
		tm.put(R.id.itemDiassemble, "diassemble");
		tm.put(R.id.itemComment, "comment");
		for (int i = 0; i < tm.size(); i++) {
			Integer fieldsID = tm.keyAt(i);
			String fieldsName = tm.get(fieldsID);
			TextView itemElement = (TextView) mainItem.findViewById(fieldsID);
			itemElement.setText(database.getStringCell(itemInfoCur, fieldsName));
			if (fieldsName.equals("comment") && database.getStringCell(itemInfoCur, fieldsName).equals("")) {
				TextView itemCommentTitle = (TextView) mainItem.findViewById(R.id.itemCommentTitle);
				itemCommentTitle.setVisibility(View.GONE);
			}
		}
		
		OnItemClickListener gridItemClick =  new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				TextView itemname = (TextView) arg1.findViewById(R.id.itemConsistName); 
				Intent showItem = new Intent(ShowItem.this, ShowItem.class);
				showItem.putExtra("itemname", itemname.getText());
				ShowItem.this.startActivity(showItem);
				
			}
		};
		
		Cursor itemConsist = database.getItemConsistOf(inItemName, itemInfoID);
		fillGridItems(itemConsist, R.id.gridConsistOf, R.id.LayConsistOf, gridItemClick);
		
		Cursor itemIncluded = database.getItemIncluded(inItemName, itemInfoID);
		fillGridItems(itemIncluded, R.id.gridIncluded, R.id.LayIncluded, gridItemClick);
		
		/* ads */
		loadAd(this);
		/* ads */
		
		return;
	}
	
	private void fillGridItems(Cursor cursor, int gridId, int layGridParent, OnItemClickListener gridItemClick) {
		if (cursor != null && cursor.getCount() != 0) {
			FixedGridView gridIncluded = (FixedGridView) mainItem.findViewById(gridId);
			gridIncluded.setAdapter(new GridAdapterItems(ShowItem.this, cursor));
			gridIncluded.setOnItemClickListener(gridItemClick);
		} else {
			LinearLayout layIncluded = (LinearLayout) mainItem.findViewById(layGridParent);
			layIncluded.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onBackPressed() {
		if (returnBack == false) {
			Intent showItems = new Intent(ShowItem.this, Items.class);
			ShowItem.this.startActivity(showItems);
		} else {
			super.onBackPressed();
		}
	}
}
