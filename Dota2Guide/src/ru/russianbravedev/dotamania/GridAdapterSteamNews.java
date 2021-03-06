package ru.russianbravedev.dotamania;

import java.sql.Date;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapterSteamNews extends BaseAdapter {
	private JSONArray JSONSteamNews;
	private Context context;
	
	public GridAdapterSteamNews(Context context, JSONArray JSONSteamNews) {
		this.JSONSteamNews = JSONSteamNews;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return JSONSteamNews.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridView;
		JSONObject elem = null;
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.gridnews, null);
		} else {
			gridView = (View)convertView;
		}
		
		try {
			elem = JSONSteamNews.getJSONObject(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (elem != null) {
			RomulTextView gridtitle = (RomulTextView) gridView.findViewById(R.id.gridNewsTitle);
			
			try {
				gridtitle.setText(elem.getString("title"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			TextView gridauthor = (TextView) gridView.findViewById(R.id.gridNewsAuthor);
			
			try {
				gridauthor.setText(elem.getString("author"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			TextView griddate = (TextView) gridView.findViewById(R.id.gridNewsDate);
			
			Date publictime = null;
			try {
				publictime = new Date(elem.getLong("date") * 1000);
				String datestr = dateformat.format(publictime);
				griddate.setText(datestr);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		
		return gridView;
	}

}
