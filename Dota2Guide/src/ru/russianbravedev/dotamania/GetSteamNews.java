package ru.russianbravedev.dotamania;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import com.danildr.androidcomponents.GetJSONfromUrl;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class GetSteamNews extends AsyncTask<FixedGridView, Void, JSONArray> {
	private Context context;
	private JSONArray steamNews = null;
	private String lang;
	
	public GetSteamNews(Context context, String lang) {
		this.context = context;
		this.lang = lang;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();		
	}
	
	@Override
	protected JSONArray doInBackground(FixedGridView... infgv) {
		FixedGridView steamNewsGrid = infgv[0];
		
		String urlstr = "http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=570&count=25&format=json";
		JSONObject steamNewsReq =  new GetJSONfromUrl(urlstr).getJson();
		try {
			steamNews = steamNewsReq.getJSONObject("appnews").getJSONArray("newsitems");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (steamNews != null) {
			steamNewsGrid.setAdapter(new GridAdapterSteamNews(context, steamNews));
			
			steamNewsGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int newsID, long arg3) {
					JSONObject curNews = null;
					try {
						curNews = getJsonObject(steamNews, newsID);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Intent showSteamNews = new Intent(context, ShowSteamNews.class);
					showSteamNews.putExtra("lang", lang);
					try {
						showSteamNews.putExtra("newstitle", curNews.getString("title"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					TextView datetv = (TextView) arg1.findViewById(R.id.gridNewsDate);
					showSteamNews.putExtra("newsdate", datetv.getText());

					try {
						showSteamNews.putExtra("newsauthor", curNews.getString("author"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						showSteamNews.putExtra("newstext", curNews.getString("contents"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					context.startActivity(showSteamNews);
				}
			});
		}
		return steamNews;
	}
	   
    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }
    
	private JSONObject getJsonObject(JSONArray steamNews, int jsonindex) throws JSONException {
		return steamNews.getJSONObject(jsonindex);
	}

}

