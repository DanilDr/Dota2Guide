package ru.russianbravedev.dotamania;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import ru.russianbravedev.dotamania.R;

public class DotaBuffMenu extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dota_buff_menu);
		SparseArray<String> dotabuffMenu = new SparseArray<String>();
		dotabuffMenu.append(R.id.dotaBuffHeroes, "heroes");
		dotabuffMenu.append(R.id.dotaBuffItems, "items");
		dotabuffMenu.append(R.id.dotaBuffMatches, "matches");
		dotabuffMenu.append(R.id.dotaBuffPlayers, "players");
		dotabuffMenu.append(R.id.dotaBuffTeams, "teams");
		
		for (int i = 0; i < dotabuffMenu.size(); i++) {
			int menu = dotabuffMenu.keyAt(i);
			final String menustr = dotabuffMenu.valueAt(i);
			MainMenuButtons curMainMenu = (MainMenuButtons) findViewById(menu);
			curMainMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent showMmElement = new Intent(DotaBuffMenu.this, DotaBuffInfo.class);
					showMmElement.putExtra("lang", lang);
					showMmElement.putExtra("menustr", menustr);
					DotaBuffMenu.this.startActivity(showMmElement);
				}
			});
		}
	}
}
