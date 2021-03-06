package ru.russianbravedev.dotamania;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import com.danildr.cacheimageview.CacheImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridAdapterWallpapers extends BaseAdapter {
	private Context context;
	private JSONArray wallpapersArray;
	
	public GridAdapterWallpapers(Context context, JSONArray wallpapersArray) {
		this.context = context;
		this.wallpapersArray = wallpapersArray;
	}

	@Override
	public int getCount() {
		return wallpapersArray.length();
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
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.wallpaperelem, null);
		} else {
			gridView = (View)convertView;
			((CacheImageView) gridView.findViewById(R.id.wpThumbImage)).setImageResource(R.drawable.hourglass);
		}		
		try {
			JSONObject wallpaperObj = wallpapersArray.getJSONObject(position);
			CacheImageView wpThumbImage = (CacheImageView) gridView.findViewById(R.id.wpThumbImage);
			wpThumbImage.setImageUrl(wallpaperObj.getString("thumb"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return gridView;
	}

}
