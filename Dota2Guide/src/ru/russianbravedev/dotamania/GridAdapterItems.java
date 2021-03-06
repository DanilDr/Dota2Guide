package ru.russianbravedev.dotamania;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ru.russianbravedev.dotamania.R;

public class GridAdapterItems extends BaseAdapter {
	private Context context;
	private final Cursor gridcontent;
	private String[] cellstring = new String[3];
	
	public GridAdapterItems(Context context, Cursor cursor){
		this.context = context;
		this.gridcontent = cursor;
		gridcontent.moveToFirst();
	}

	@Override
	public int getCount() {
		return gridcontent.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridView;
		gridcontent.moveToPosition(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.iteminfoitem, null);
		} else {
			gridView = (View)convertView;
		}
		
		cellstring[0] = gridcontent.getString(0);
		cellstring[1] = gridcontent.getString(1);
		cellstring[2] = gridcontent.getString(2);

		TextView itemConsistName = (TextView)gridView.findViewById(R.id.itemConsistName);
		itemConsistName.setText(cellstring[0]);
		TextView itemConsistCost = (TextView)gridView.findViewById(R.id.itemConsistCost);
		if (cellstring[2].equals("")) {
			View parentCost = (View) itemConsistCost.getParent();
			parentCost.setVisibility(View.GONE);
		} else {
			itemConsistCost.setText(cellstring[2]);
		}
		ImageView imageView = (ImageView)gridView.findViewById(R.id.itemConnsistIcon);
		String uri = "drawable/" + cellstring[1];
		if (uri.endsWith(".png")) {
			uri = uri.substring(0, uri.length() - 4);
		}
		int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		imageView.setImageResource(imageResource);

		return gridView;
	}
}
