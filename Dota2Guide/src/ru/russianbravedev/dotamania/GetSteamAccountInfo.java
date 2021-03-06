package ru.russianbravedev.dotamania;

import org.json.JSONObject;

import com.danildr.androidcomponents.GetJSONfromUrl;

import android.os.AsyncTask;

public class GetSteamAccountInfo extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params) {
		final String steamIDUrl = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + params[1] + "&steamids=" + params[0];
    	JSONObject steamJson = new GetJSONfromUrl(steamIDUrl).getJson();
		return steamJson;
	}

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
