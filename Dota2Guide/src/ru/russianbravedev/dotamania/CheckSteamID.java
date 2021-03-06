package ru.russianbravedev.dotamania;

import org.json.JSONException;
import org.json.JSONObject;

import com.danildr.androidcomponents.GetJSONfromUrl;

import android.os.AsyncTask;

public class CheckSteamID extends AsyncTask<String, Void, Boolean> {
	@Override
	protected Boolean doInBackground(String... params) {
		int numsteamplayers = 0;
		Boolean checkStatus = false;
		String steamID = params[0];
		final String steamIDUrl = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + params[1] + "&steamids=" + steamID;
    	JSONObject steamJson = new GetJSONfromUrl(steamIDUrl).getJson();
        try {
			numsteamplayers = steamJson.getJSONObject("response").getJSONArray("players").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (numsteamplayers > 0) {checkStatus = true;}

        return checkStatus;
	}
	
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }


}
