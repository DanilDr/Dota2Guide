package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import ru.russianbravedev.dotamania.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapterStoreItems extends BaseAdapter {
	private Context context;
	private final Cursor gridcontent;
	private String[] cellstring = new String[3];
	
	public GridAdapterStoreItems(Context context, Cursor cursor){
		this.context = context;
		this.gridcontent = cursor;
		gridcontent.moveToFirst();
	}
	
	@Override
	public int getCount() {
		return this.gridcontent.getCount();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
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
		ImageView imageView = (ImageView)gridView.findViewById(R.id.cellimage);
		cellstring[0] = gridcontent.getString(0);
		cellstring[1] = gridcontent.getString(1);
		textView.setText(cellstring[0]);
		
		try {
			imageView.setImageBitmap(new DownloadImageTask().execute(cellstring[1]).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return gridView;
	}

}
