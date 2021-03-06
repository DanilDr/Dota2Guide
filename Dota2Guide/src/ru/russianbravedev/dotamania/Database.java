package ru.russianbravedev.dotamania;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.text.TextUtils;

public class Database extends SQLiteOpenHelper {
	private final Context context;
	private String DB_PATH; // путь к файлам при использовании внутренней памяти
	private String DB_FULLPATH; // полный путь к файлу БД
	private static final String DB_FOLDERNAME = "/dota2guide/database/"; // путь в случае использования внешнего носителя
	private static final String DB_FULLFOLDERNAME = Environment.getExternalStorageDirectory() + DB_FOLDERNAME; // полный путь к папке с БД при использовании внешнего носителя
	private static final String DB_NAME = "dota2.db"; // название файла БД
	private static final int DB_VER = 1; // версия бахы данных
	private SQLiteDatabase dotaDB; // база данных 
	
	public Database(Context context) {
		super(context, DB_NAME, null, DB_VER);
		this.context = context;
		File storage = new File(DB_FULLFOLDERNAME);
		if (!storage.exists() && !storage.mkdirs()) {
			DB_PATH = context.getFilesDir().getPath();
			DB_FULLPATH = DB_PATH + File.separator + DB_NAME;
			this.getReadableDatabase();
		} else {
			DB_FULLPATH = DB_FULLFOLDERNAME + DB_NAME;
		}
	}
	
	public void checkDatabase() throws IOException {
		try {
			// получение MD5 входящей базы
			String inputsb = getMD5(context.getAssets().open(DB_NAME)).toString();
	        // получение MD5 текущей базы
	        String currentsb = getMD5(new FileInputStream(DB_FULLPATH)).toString();
	        Boolean resultCompare = inputsb.equals(currentsb); 
	        if (resultCompare == false) {
	        	createDatabase();
	        }
		} catch (NoSuchAlgorithmException | IOException e) {
			createDatabase();
		}
	}
	
	private StringBuffer getMD5(InputStream input) throws NoSuchAlgorithmException, IOException {
		int length;
		byte[] buffer = new byte[1024]; // буфер
		MessageDigest mdcur = MessageDigest.getInstance("MD5");
		while ((length = input.read(buffer))>0) { // подсчет размера БД
			mdcur.update(buffer, 0, length);
		}
		StringBuffer sb = new StringBuffer();
		byte[] mdbytes = mdcur.digest();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb;
	}

	private void createDatabase() {
		try {
			copyDataBase();
		} catch (IOException e) {
			//throw new Error("Error copying database");
		}
	}
	
	private void copyDataBase() throws IOException {
		InputStream input = context.getAssets().open(DB_NAME);
		OutputStream output = new FileOutputStream(DB_FULLPATH);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer))>0) {
			output.write(buffer, 0, length);
		}
		output.flush();
		output.close();
		input.close();
	}
	
	public void openDataBase() throws SQLException {
		dotaDB = SQLiteDatabase.openDatabase(DB_FULLPATH, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	@Override
	public synchronized void close() {
		if (dotaDB != null) {
			dotaDB.close();
		}
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	private Cursor getHeroes(String herotype, String lang) throws SQLException{
		String idHeroType = getHeroTypeID(herotype);	
		return dotaDB.query("heroes", new String[]{"name", "bigimage"}, "type='" + idHeroType + "' and lang='" + lang + "'", null, null, null, "name", null);
	}
	
	// получение id типа героя по названию 
	private String getHeroTypeID(String HeroType) {
		Cursor cursortype = dotaDB.query("heroes_type", new String[] {"_id"}, "type='" + HeroType + "'", null, null, null, null, "1");
		cursortype.moveToFirst();
		return cursortype.getString(cursortype.getColumnIndex("_id"));
	}
	
	// получение списка герое по типу и языу
	public Cursor getListHeroes(String HeroType, String lang) {
		Cursor cursor;
		try {
			cursor = getHeroes(HeroType, lang);
		} catch (SQLException sqle) {
			throw new Error("DB Error");
		}
		return cursor;
	}
	
	// получение информации о герое по имени
	public Cursor getHeroByName(String heroName, String lang) throws SQLException {
		openDataBase();
		Cursor cursor = dotaDB.query("heroes", new String[] {"_id", "name", "description", "status", "image", "params", "levelparams", "attackparam", "viewdaynight" }, "name=\"" + heroName + "\" and lang='" + lang + "'", null, null, null, null, "1");
		cursor.moveToFirst();
		return cursor;
	}
	
	// получение ячейки курсора
	public String getStringCell(Cursor cursor, String cellName) {
		return cursor.getString(cursor.getColumnIndex(cellName));
	}
	
	// получение способностей героя по имени и языку
	public Cursor getHeroAbilitiesByName(String heroName, String lang) throws SQLException {
		Cursor curHero = getHeroByName(heroName, lang);
		String heroID = getStringCell(curHero, "_id");
		return dotaDB.query("abilities", new String[] {"name","description", "ability", "params", "mana", "cooldown", "image", "note", "aganim", "youtube" }, "hero_id='" + heroID + "'", null, null, null, null);
	}
	
	// запрос гайдов по имени героя и языку
	public Cursor getHeroGuidesByName(String heroName, String lang) throws SQLException {
		String curHeroID = getStringCell(getHeroByName(heroName, lang), "_id");
		Cursor cursorCurHero = (Cursor) dotaDB.query("guides", new String[] {"about", "advice", "facts", "urlpage"}, "hero_id='" + curHeroID + "'", null, null, null, null);
		cursorCurHero.moveToFirst();
		return cursorCurHero;
	};
	
	// получение списка используемых предметов героем по имени и языку
	public Cursor getHeroGuidesItems(String heroName, String lang, Integer type) throws SQLException {
		String curHeroID = getStringCell(getHeroByName(heroName, lang), "_id");
		Cursor cursorHeroItems = (Cursor) dotaDB.query("heroes_items", new String[] {"item_id"}, "hero_id='" + curHeroID + "'  and type='" + type.toString() + "'",
				null, null, null, null);
		return getItemsCursor(cursorHeroItems);
	}
	
	// получение списка предметов по типу и языку
	public Cursor getListItems(String itemsType, String lang) {
		String idHeroType = getElementsTypeID("items_type", itemsType);	
		return dotaDB.query("items", new String[]{"name", "image", "cost"}, "type_id='" + idHeroType + "' and lang='" + lang + "'", null, null, null, null, null);
	}

	// получение предмета по имени и языку
	public Cursor getItemByName(String itemName, String lang) throws SQLException{
		Cursor cursor = dotaDB.query("items", new String[] {"_id", "name", "image", "cost", "description", "description2", "comment", "diassemble" }, "name=\"" + itemName + "\" and lang=\"" + lang + "\"", null, null, null, null, "1");
		cursor.moveToFirst();
		return cursor;
	}
	
	// получения списка предметов из которых состоит текущий
	public Cursor getItemConsistOf(String itemName, int itemID) {
		Cursor cursorConsistOf = dotaDB.query("items_from", new String[] {"item_from_id"}, "item_id=" + itemID, null, null, null, null);
		return getItemsCursor(cursorConsistOf);
	}
	
	// получение списка предметов в состав которых входит данный предмет
	public Cursor getItemIncluded(String itemName, int itemID) {
		try {
			openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Cursor cursorInclude = dotaDB.query("items_from", new String[] {"item_id"}, "item_from_id=" + itemID, null, null, null, null);
		return getItemsCursor(cursorInclude);
	}
	
	// получение списка предметов
	private Cursor getItemsCursor(Cursor cursor) {
		ArrayList<Integer> listIDCO = new ArrayList<Integer>();
		while (cursor.moveToNext()) {
			listIDCO.add(Integer.parseInt(cursor.getString(0)));
		}
		Cursor outCursor = dotaDB.query("items", new String[] {"name", "image", "cost"}, "_id in (" + TextUtils.join(",", listIDCO.toArray())
				+ ")", null, null, null, "name");
		return outCursor;
	}

	private String getElementsTypeID(String typeTable, String elemType) {
		Cursor cursortype = dotaDB.query(typeTable, new String[] {"_id"}, "type='" + elemType + "'", null, null, null, null, "1");
		cursortype.moveToFirst();
		return cursortype.getString(cursortype.getColumnIndex("_id"));
	}
	public Cursor getTactics(String lang) {
		Cursor curtactics = dotaDB.query("tactics", new String[] {"name"}, "lang='" + lang + "'", null, null, null, "_id");
		return curtactics;
	}
	public Cursor getInfoTactic(String tacticName, String lang) {
		Cursor curTactic = dotaDB.query("tactics", new String[] {"_id"}, "name='" + tacticName + "' and lang='" + lang + "'", null, null, null, "_id", "1");
		curTactic.moveToFirst();
		String curTacticId = getStringCell(curTactic, "_id");
		return dotaDB.query("tactics_desc", new String[] {"name", "description"}, "tactics_id=" + curTacticId, null, null, null, "_id");
	}
	public Cursor getCrepsInfo(String crepsType, String lang) {
		Cursor crepsCursor = dotaDB.query("creps", new String[] {"name", "description", "image", "params"}, "type='" + crepsType + "' and lang='" + lang + "'", null, null, null, null, "1");
		crepsCursor.moveToFirst();
		return crepsCursor;
	}
	
	public Cursor getCrepsTypes(String lang) {
		Cursor crepsTypes = dotaDB.query("creps", new String[] {"name", "type"}, "lang='" + lang + "' and type!='union'", null, null, null, null, null);
		return crepsTypes;
	}
	public Cursor getStoreItems(String lang) {
		return dotaDB.query("items_store", new String[] {"name", "image_url"}, "lang='" + lang + "' and image_url != ''", null, null, null, "name", null);
	}
	
	public Cursor getStoreItem(String itemName, String lang) {
		Cursor curStoreItem = dotaDB.query("items_store", new String[] {"name", "item_class", "item_description", "item_quality", "min_ilevel", "max_ilevel", 
				"image_url_large", "cap_can_craft_mark", "cap_can_be_restored", "cap_strange_parts", "cap_paintable_unusual", 
				"cap_autograph", "cap_can_consume", "item_type_name"}, "lang='" + lang + "' and name = \"" + itemName + "\"", null, null, null, null, "1");
		curStoreItem.moveToFirst();
		return curStoreItem;
	}
	
	public Cursor getStoreAccountItems(String lang, String listitems) {
		Cursor curStoreItem = dotaDB.query("items_store", new String[] {"name", "image_url"}, "lang='" + lang + "' and defindex in (" + listitems + ")", null, null, null, null, null);
		return curStoreItem;
	}

	public String getHeroesNameBySteamID(ArrayList<Integer> herosteamid) {
		ArrayList<String> playersname = new ArrayList<String>(); 
		Cursor cursor = dotaDB.query("heroes", new String[] {"name"}, "steam_id in (" + TextUtils.join(", ", herosteamid) + ")", null, null, null, null);
		while (cursor.moveToNext()) {
			playersname.add(getStringCell(cursor, "name"));
		}
		return TextUtils.join(", ", playersname);
	}
	
	public String getHeroNameBySteamID(Integer herosteamid) {
		Cursor cursor = dotaDB.query("heroes", new String[] {"name"}, "steam_id=" + herosteamid, null, null, null, null, "1");
		cursor.moveToFirst();
		return getStringCell(cursor, "name");
	}

}
