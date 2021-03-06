package ru.russianbravedev.dotamania;

import org.json.JSONException;
import org.json.JSONObject;

import com.danildr.androidcomponents.GetJSONfromUrl;

import android.os.AsyncTask;

public class GetMatchInfo extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params) {
		String urlstr = "http://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?match_id=" + params[0] + "&key=" + params[1];
		JSONObject matchInfoJSON = null;
		try {
			JSONObject matchResultJSON = new GetJSONfromUrl(urlstr).getJson(); 
			matchInfoJSON = matchResultJSON.getJSONObject("result");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return matchInfoJSON;
	}
@Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }

}
