package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.danildr.androidcomponents.LoadingJSONfromURL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Wallpapers extends FullScreenActivity {
	private JSONObject wallpapersJSON = null;
	private String status = null;
	private GridView gridWallpaper;
	private JSONArray wallpapersArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallpapers);
		
        String wallpapersURL = getString(R.string.servicehost) + "/wallpapers";
		try {
			wallpapersJSON = new LoadingJSONfromURL().execute(wallpapersURL).get(20, TimeUnit.SECONDS);
			if (wallpapersJSON != null) {
				status = wallpapersJSON.getString("result");
			}
		} catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
			e.printStackTrace();
		}
		if (status != null && status.equals("success")) {
			try {
				wallpapersArray = wallpapersJSON.getJSONArray("images");
				gridWallpaper = (GridView) findViewById(R.id.gridWallpaper);
				gridWallpaper.setAdapter(new GridAdapterWallpapers(this, wallpapersArray));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			gridWallpaper.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					JSONObject getWallpaper;
					try {
						getWallpaper = wallpapersArray.getJSONObject(position);
						Intent showWallpaper = new Intent(Wallpapers.this, ShowWallpaper.class);
						showWallpaper.putExtra("imageURL", getWallpaper.getString("image"));
						Wallpapers.this.startActivity(showWallpaper);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			Toast.makeText(this, getString(R.string.servicenotavailable), Toast.LENGTH_SHORT).show();
		}
		/* ads */
		loadAd(this);
		/* ads */
	}
}
