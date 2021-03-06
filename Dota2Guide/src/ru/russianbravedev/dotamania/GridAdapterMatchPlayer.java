package ru.russianbravedev.dotamania;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
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

public class GridAdapterMatchPlayer extends BaseAdapter{
	private Context context;
	private JSONArray matchPlayers;
	private ArrayList<JSONObject> jsonlist = null;
	private JSONObject apiHeroes;
	private String steamkey;
	
	GridAdapterMatchPlayer (Context context, JSONArray matchPlayers, Boolean radian, JSONObject apiHeroes, String steamkey) {
		this.context = context;
		this.matchPlayers = matchPlayers;
		this.apiHeroes = apiHeroes;
		this.steamkey = steamkey;
		jsonlist = new ArrayList<JSONObject>();
		for (int i = 0; i < this.matchPlayers.length(); i++) {
			Integer playerslot = null;
			try {
				playerslot = this.matchPlayers.getJSONObject(i).getInt("player_slot");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (playerslot != null) {
				if (radian == true && playerslot < 127) {
					try {
						JSONObject matchPlayerObject = this.matchPlayers.getJSONObject(i); 
						jsonlist.add(matchPlayerObject);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (radian == false && playerslot > 127) {
					try {
						JSONObject matchPlayerObject = this.matchPlayers.getJSONObject(i); 
						jsonlist.add(matchPlayerObject);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public int getCount() {
		return jsonlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridView = null;
		JSONObject curPlayer = jsonlist.get(position);
		Map<Integer, String> aboutPlayer = new TreeMap<Integer, String>();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.playermatchinfo, null);
		} else {
			gridView = (View)convertView;
		}
		
		if (curPlayer != null) {

			String account_id = null;
			String steamname = null;
			try {
				account_id = (new ConverterSteamID().Steam32To64(curPlayer.getLong("account_id"))).toString();
				JSONObject accountJSON = new GetSteamAccountInfo().execute(account_id, this.steamkey).get();
				steamname = accountJSON.getJSONObject("response").getJSONArray("players").getJSONObject(0).getString("personaname");
			} catch (JSONException e2) {
				e2.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (steamname == null) { steamname = account_id; } 
			aboutPlayer.put(R.id.gridPlayersInfoSteamName, steamname);

			try {
				aboutPlayer.put(R.id.gridPlayersInfoHeroName, ((JSONObject) apiHeroes.getJSONObject("result").getJSONArray("heroes").get(curPlayer.getInt("hero_id"))).getString("localized_name"));
				aboutPlayer.put(R.id.gridPlayersInfoLevel, curPlayer.getString("level"));
				aboutPlayer.put(R.id.playerkills, curPlayer.getString("kills"));
				aboutPlayer.put(R.id.playerdeaths, curPlayer.getString("deaths"));
				aboutPlayer.put(R.id.playerassists, curPlayer.getString("assists"));
				aboutPlayer.put(R.id.playergold, curPlayer.getString("gold"));
				aboutPlayer.put(R.id.playerlasthists, curPlayer.getString("last_hits"));
				aboutPlayer.put(R.id.playerdenies, curPlayer.getString("denies"));
				aboutPlayer.put(R.id.playergoldmin, curPlayer.getString("gold_per_min"));
				aboutPlayer.put(R.id.playerxpmin, curPlayer.getString("xp_per_min"));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			for (Entry<Integer, String> entry : aboutPlayer.entrySet()) {
				setTextField(gridView, entry.getKey(), entry.getValue());
			}
			
			// добавление изображения героя
//			ImageView heroImageMatchHistory = (ImageView) gridView.findViewById(R.id.heroImageMatchHistory);
			
			
		}
		return gridView;
	}
	
	private void setTextField(View gridView, Integer TextFieldID, String TextFieldString) {
		TextView gridPlayersInfo = (TextView) gridView.findViewById(TextFieldID);
		gridPlayersInfo.setText(TextFieldString);
		return;
	}

}
