package ru.russianbravedev.dotamania;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import com.danildr.androidcomponents.LoadingJSONfromURL;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class FunnyVideoMenu extends FullScreenActivity {
	private JSONObject funnyvideoJSON;
	private String status;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_funny_video_menu);
		// мапировка кнопки и источника видео
		SparseArray<String> buttonsSource = new SparseArray<String>();
		buttonsSource.append(R.id.funnyvideo1, "dotamon");
		buttonsSource.append(R.id.funnyvideo2, "dota2mythbusters");
		buttonsSource.append(R.id.funnyvideo3, "dota2reporter");
		buttonsSource.append(R.id.funnyvideo4, "dota2fails");
		String wallpapersURL = getString(R.string.servicehost) + "/funnyvideo";
		try {
			funnyvideoJSON = new LoadingJSONfromURL().execute(wallpapersURL).get(10, TimeUnit.SECONDS);
			if (funnyvideoJSON != null) {
				status = funnyvideoJSON.getString("result");
			}
		} catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
			e.printStackTrace();
		}
		if (status != null && status.equals("success")) {
			try {
				JSONObject videoJSON = funnyvideoJSON.getJSONObject("video");
				for (int i = 0; i < buttonsSource.size(); i++) {
					Integer buttonID = buttonsSource.keyAt(i);
					String sourceType = buttonsSource.get(buttonID);
					final JSONArray videoSourceJSON = videoJSON.getJSONArray(sourceType);
					final ArrayList<String> listVideos = new ArrayList<String>();
					for (int j = 0; j < videoSourceJSON.length(); j++) {
						JSONObject currentVideo = videoSourceJSON.getJSONObject(j);
						listVideos.add(currentVideo.getString("name") + "@" + currentVideo.getString("link"));
					}
					MainMenuButtons currentButton = (MainMenuButtons) findViewById(buttonID);
					currentButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(FunnyVideoMenu.this, FunnyVideoList.class);
							intent.putExtra("listvideos", listVideos.toArray(new String[listVideos.size()]));
							FunnyVideoMenu.this.startActivity(intent);
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, getString(R.string.servicenotavailable), Toast.LENGTH_SHORT).show();
		}
	}
}
