package ru.russianbravedev.dotamania;

import org.json.JSONObject;

import com.danildr.androidcomponents.GetJSONfromUrl;

import android.os.AsyncTask;

public class GetAccountMatches extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params) {
		String urlstr = params[0];
		JSONObject matchesjson = new GetJSONfromUrl(urlstr).getJson();
		return matchesjson;
	}

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
