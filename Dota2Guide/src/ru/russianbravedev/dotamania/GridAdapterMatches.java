package ru.russianbravedev.dotamania;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
import com.danildr.androidcomponents.LoadingJSONfromURL;

public class GridAdapterMatches extends BaseAdapter {
	private JSONArray matchesjson;
	private Context context;
	private ArrayList<Integer> heroeslist = new ArrayList<Integer>();
	private JSONObject apiHeroes = null;
	
	public GridAdapterMatches(Context context, JSONArray matchesjson, String lang) {
		this.matchesjson = matchesjson;
		this.context = context;
		String urlheroes = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=" + context.getString(R.string.steamkey) + "&language=" + lang;
		try {
			apiHeroes = new LoadingJSONfromURL().execute(urlheroes).get(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getCount() {
		return 10;//return matchesjson.length();
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
		JSONObject elem = null;
		JSONArray listplayers = null;
		Long timesec = null;
		Long matchid = null;
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.matchinfogrid, null);
		} else {
			gridView = (View)convertView;
		}
		try {
			elem = matchesjson.getJSONObject(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (elem != null) {
			
			try {
				matchid = elem.getLong("match_id");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			if (matchid != null ) {
				TextView matchidtv = (TextView) gridView.findViewById(R.id.gridmatchid);
				matchidtv.setText(matchid.toString());
			}
			
			
			try {
				timesec = elem.getLong("start_time");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (timesec != null) {
				Date starttime = new Date(timesec * 1000);
				String datestr = dateformat.format(starttime);
				TextView datetimetv = (TextView) gridView.findViewById(R.id.gridmatchdate);
				datetimetv.setText(datestr);
			}
			
			try {
				listplayers = elem.getJSONArray("players");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if (listplayers != null) {
				heroeslist.clear();
				for (int i = 0; i < listplayers.length(); i++) {
					try {
						JSONObject matchplayerinfo = listplayers.getJSONObject(i);
						if (matchplayerinfo.getInt("hero_id") != 0) {
							heroeslist.add(matchplayerinfo.getInt("hero_id"));
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				
				
				ArrayList<String> heroesliststr = new ArrayList<String>();
				for (int i: this.heroeslist) {
					try {
						heroesliststr.add(((JSONObject) apiHeroes.getJSONObject("result").getJSONArray("heroes").get(i)).getString("localized_name"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				TextView heroeslisttv = (TextView) gridView.findViewById(R.id.gridmatchplayers);
				heroeslisttv.setText(heroesliststr.toString());
			}
			
		}
		return gridView;
	}

}
