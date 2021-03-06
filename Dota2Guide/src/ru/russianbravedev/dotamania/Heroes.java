package ru.russianbravedev.dotamania;

import java.sql.SQLException;
import ru.russianbravedev.dotamania.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Heroes extends FullScreenActivity {
	private GridView gridheroes;
	private Database database;
	private final String[] heroTypeList = {"force", "agility", "intelligence"};
	private int curHeroTypeId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heroes);
		final RomulTextView forcebtn = (RomulTextView)findViewById(R.id.forcebtn);
		final RomulTextView agilitybtn = (RomulTextView)findViewById(R.id.agilitybtn);
		final RomulTextView intellegencybtn = (RomulTextView)findViewById(R.id.intellegencybtn);
		gridheroes = (GridView)findViewById(R.id.heroesgrid);
		database = new Database(this);
		
		try {
			database.openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		forcebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forcebtn.setBackgroundColor(additColors[0][1]);
				agilitybtn.setBackgroundColor(additColors[1][0]);
				intellegencybtn.setBackgroundColor(additColors[2][0]);
				gridheroes.setAdapter(new GridAdapter(Heroes.this, database.getListHeroes(heroTypeList[0], lang)));
				curHeroTypeId = 0;
			}
		});
		
		agilitybtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forcebtn.setBackgroundColor(additColors[0][0]);
				agilitybtn.setBackgroundColor(additColors[1][1]);
				intellegencybtn.setBackgroundColor(additColors[2][0]);
				gridheroes.setAdapter(new GridAdapter(Heroes.this, database.getListHeroes(heroTypeList[1], lang)));
				curHeroTypeId = 1;
			}
		});
		
		intellegencybtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forcebtn.setBackgroundColor(additColors[0][0]);
				agilitybtn.setBackgroundColor(additColors[1][0]);
				intellegencybtn.setBackgroundColor(additColors[2][1]);
				gridheroes.setAdapter(new GridAdapter(Heroes.this, database.getListHeroes(heroTypeList[2], lang)));
				curHeroTypeId = 2;
			}
		});
		
		gridheroes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, final int position,
					long id) {
				TextView heroname = (TextView) v.findViewById(R.id.celltext); 
				Intent showHero = new Intent(Heroes.this, ShowHero.class);
				showHero.putExtra("heroname", heroname.getText());
				showHero.putExtra("herotypeid", curHeroTypeId);
				Heroes.this.startActivity(showHero);
			}
		});
		
		forcebtn.performClick();

		/* ads */
		loadAd(this);
		/* ads */
	}
	
	@Override
	public void onBackPressed() {
		Intent showMainMenu = new Intent(Heroes.this, MainMenu.class);
		Heroes.this.startActivity(showMainMenu);
	}

}
