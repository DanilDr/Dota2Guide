package ru.russianbravedev.dotamania;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.util.Log;

class GetSteamIDbyName extends AsyncTask<String, Void, Long> {

    protected Long doInBackground(String... params) {
    	String steamName = params[0];
    	String inputSteamXml = "";
    	String inputLine = "";
    	Long steamID = null;
		String checksteamname = "http://steamcommunity.com/id/" + steamName + "/?xml=1";
		String XMLSteamID = "<steamID64>(\\d+)</steamID64>";
		Pattern patSteamID = Pattern.compile(XMLSteamID);
        try {
        	URL url = new URL(checksteamname);
            InputStream in = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ( (inputLine = br.readLine()) != null) {
            	inputSteamXml += inputLine; 
            };
            br.close();
            in.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
		Matcher matchSteamID = patSteamID.matcher(inputSteamXml);
		matchSteamID.find();
		try {
			steamID = new Long(matchSteamID.group(1));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
        return steamID;
    }
   
    @Override
    protected void onPostExecute(Long result) {
    	if (result == null) {
    		super.onPostExecute(null);
    	} else {
    		super.onPostExecute(new Long(result));
    	}
    }
}

