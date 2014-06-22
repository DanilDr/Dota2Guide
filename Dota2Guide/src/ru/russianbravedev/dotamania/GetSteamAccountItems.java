package ru.russianbravedev.dotamania;

//import java.util.ArrayList;
//import java.util.List;
//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.danildr.androidcomponents.GetJSONfromUrl;

import android.os.AsyncTask;
import android.util.Log;

public class GetSteamAccountItems extends AsyncTask<String, Void, JSONArray> {

	protected JSONArray doInBackground(String... params) throws SecurityException {
		String apikey = params[1];
		String steamid = params[0];
		String urlstr = "http://api.steampowered.com/IEconItems_570/GetPlayerItems/v0001/?key=" + apikey + "&SteamID=" + steamid;
		JSONObject steamJson = new GetJSONfromUrl(urlstr).getJson();
		JSONObject results = null;
		JSONArray resultItems = null;
		int statusresult = 0;
		try {
			results = (JSONObject) steamJson.getJSONObject("result");
			statusresult = (int) results.getLong("status");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if (statusresult == 1) {
			try {
				resultItems = results.getJSONArray("items");
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		} else {
			Log.e("error", "Permission denied");
		}
		
		return resultItems;
	}

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }
}
