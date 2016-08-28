package reza.sabbagh.buildingmanagment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
	
	private final Context mycontext;
	public final String path = "data/data/reza.sabbagh.buildingmanagment/databases/";
	public final String dbname = "building_db.db3";
	public SQLiteDatabase mydb;
	
	public database(Context context) {
		super(context, "building_db.db3", null, 1);
		mycontext = context;
	}
	public void onCreate(SQLiteDatabase db) {
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public boolean checkdb(){
		SQLiteDatabase db = null;
		try{
		db = SQLiteDatabase.openDatabase(path + dbname, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLException e)
		{
			
		}
		return db != null ? true:false;
	}
	public void databasecreate(){
		boolean checkdb = checkdb();
		if(checkdb){
			
		}else{
			this.getReadableDatabase();
			try{
			copydatabase();
			}catch(IOException e){
			}
		}
	}
	public void copydatabase() throws IOException{
		OutputStream myOutput = new FileOutputStream(path + dbname);
		byte[] buffer = new byte[1024];
		int length;
		InputStream myInput = mycontext.getAssets().open("building_db.db3");
		while((length = myInput.read(buffer)) > 0){
			myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();
	}
	public void open(){
		mydb = SQLiteDatabase.openDatabase(path + dbname, null, SQLiteDatabase.OPEN_READWRITE);
	}
	public void close(){
		mydb.close();
	}

	public String queryUsers(int fild){
		Cursor cu = mydb.rawQuery("SELECT * FROM Users WHERE UserID=1", null);
		cu.moveToFirst();
		String result = cu.getString(fild);
		return result;
	}
	public void updateUsers(String field, String value){
		ContentValues cv = new ContentValues();
		cv.put(field, value);
		mydb.update("Users", cv, "UserID=1", null);
	}
	public Integer count(String table){
		Cursor cu = mydb.query(table, null, null, null, null, null, null);
		int count = cu.getCount();
		return count;
	}
	public void insertAyat(String field,String value){
		ContentValues cv = new ContentValues();
		cv.put(field, value);
		mydb.insert("ayatdata", null, cv);
	}
}