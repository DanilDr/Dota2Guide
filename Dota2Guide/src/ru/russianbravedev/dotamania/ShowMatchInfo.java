package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.danildr.androidcomponents.LoadingJSONfromURL;

public class ShowMatchInfo extends FullScreenActivity {
	private Database database;
	private FixedGridView gridMatchPlayers;
	private JSONObject apiHeroes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_match_info);
		String urlheroes = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=" + getString(R.string.steamkey) + "&language=" + lang;
				
		try {
			apiHeroes = new LoadingJSONfromURL().execute(urlheroes).get(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		String matchid = getIntent().getStringExtra("matchid");
		gridMatchPlayers = (FixedGridView) findViewById(R.id.gridMatchPlayers);
		RomulTextView matchWinner = (RomulTextView) findViewById(R.id.matchWinner);
		final RomulTextView showRadianPlayers = (RomulTextView) findViewById(R.id.showRadianPlayers);
		final RomulTextView showDirePlayers = (RomulTextView) findViewById(R.id.showDirePlayers);
		TextView matchDurationTv = (TextView) findViewById(R.id.matchDuration);
		database = new Database(this);
		try {
			database.openDataBase();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		JSONObject matchInfoJSON = null;
		try {
			matchInfoJSON = new GetMatchInfo().execute(matchid, getString(R.string.steamkey)).get();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		if (matchInfoJSON != null) {
			try {
				Boolean matchWinnerSide = matchInfoJSON.getBoolean("radiant_win");
				if (matchWinnerSide == true) {
					matchWinner.setText(getString(R.string.radianwin));
				} else {
					matchWinner.setText(getString(R.string.direwin));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			try {
				
				Integer matchDuration = new Integer(matchInfoJSON.getInt("duration"));
				matchDurationTv.setText((matchDuration / 60) + ":" + (matchDuration % 60));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		};

		
		JSONArray matchJSONPlayers = null;
		
		try {
			matchJSONPlayers = matchInfoJSON.getJSONArray("players");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (matchJSONPlayers != null ) {
			final JSONArray matchJSONPlayersClc = matchJSONPlayers;
		
			showRadianPlayers.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showRadianPlayers.setBackgroundColor(colors[1]);
					showDirePlayers.setBackgroundColor(colors[0]);
					gridMatchPlayers.setAdapter(new GridAdapterMatchPlayer(ShowMatchInfo.this, matchJSONPlayersClc, true, apiHeroes, getString(R.string.steamkey)));
				}
			});
			
			showDirePlayers.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showRadianPlayers.setBackgroundColor(colors[0]);
					showDirePlayers.setBackgroundColor(colors[1]);
					gridMatchPlayers.setAdapter(new GridAdapterMatchPlayer(ShowMatchInfo.this, matchJSONPlayersClc, false, apiHeroes, getString(R.string.steamkey)));
				}
			});
			
			showRadianPlayers.performClick();
		}
	}
}
