package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MatchHistory extends FullScreenActivity {
	GridView matchesGrid = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (steamid == 0) {
			Intent nextActivity = new Intent(MatchHistory.this, SteamEnterAccount.class);
			nextActivity.putExtra("lang", lang);
			nextActivity.putExtra("classname", MatchHistory.class.toString());
			MatchHistory.this.startActivity(nextActivity);
		} else {
			setContentView(R.layout.activity_match_history);
//			long account_id = new ConverterSteamID().Steam64To32(steamid);
			String urlstr = "http://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=" + getString(R.string.steamkey) + "&limit=10";// + "&account_id=" + account_id;
			JSONObject matchaccstat = null;
			JSONObject JSONResult = null;
			try {
				
				matchaccstat = new GetAccountMatches().execute(urlstr).get(20, TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			try {
				JSONResult = matchaccstat.getJSONObject("result");
				if (matchaccstat != null && JSONResult.getInt("status") == 15) {
					Toast.makeText(MatchHistory.this, getString(R.string.erroraccount), Toast.LENGTH_SHORT).show();
				} else {
					matchesGrid = (GridView) findViewById(R.id.matchesgrid);
					JSONArray matchesarraty = JSONResult.getJSONArray("matches");
					matchesGrid.setAdapter(new GridAdapterMatches(MatchHistory.this, matchesarraty, lang));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			if (matchesGrid != null) {
				matchesGrid.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
						Intent showMatchInfo = new Intent(MatchHistory.this, ShowMatchInfo.class);
						showMatchInfo.putExtra("matchid", ((TextView) arg1.findViewById(R.id.gridmatchid)).getText());
						showMatchInfo.putExtra("lang", lang);
						MatchHistory.this.startActivity(showMatchInfo);
					}
				});
			}
		}
	}
}
