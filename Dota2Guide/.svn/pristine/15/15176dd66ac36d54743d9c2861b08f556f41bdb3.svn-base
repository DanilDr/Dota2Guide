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

public class GridAdapter extends BaseAdapter {
	private Context context;
	private final Cursor gridcontent;
	private String[] cellstring = new String[2];

	public GridAdapter(Context context, Cursor cursor){
		this.context = context;
		this.gridcontent = cursor;
	}
	
	@Override
	public int getCount() {
		int lenghtAdapter = 0;
		try {
			lenghtAdapter = this.gridcontent.getCount();
		} catch (NullPointerException e) {
			
		}
		return lenghtAdapter;
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
			gridView = inflater.inflate(R.layout.herocell, null);
		} else {
			gridView = (View)convertView;
		}
		
		TextView textView = (TextView)gridView.findViewById(R.id.celltext);
		cellstring[0] = gridcontent.getString(0);
		cellstring[1] = gridcontent.getString(1);
		textView.setText(cellstring[0]);
		ImageView imageView = (ImageView)gridView.findViewById(R.id.cellimage);
		String uri = "drawable/" + cellstring[1];
		if (uri.endsWith(".png")) {
			uri = uri.substring(0, uri.length() - 4);
		}
		int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		imageView.setImageResource(imageResource);

		return gridView;
	}
}
