package com.jilinmei.bonuscalculator;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
	private static final String TAG = "DBAdapter";
	
	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "staff";
	static final int DATABASE_VERSION = 1;
	
	static final String COLUMN_ID = "_id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_INCOME = "income";
	static final String COLUMN_BONUS = "bonus";
	static final String COLUMN_PHONE = "phone";
	static final String COLUMN_CREATED_AT = "created_at";
	
	static final String SQL_CREATE_TABLE =
			"create table staff (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"name TEXT not null, income REAL not null, bonus REAL not null," +
			"phone TEXT, created_at TEXT NOT NULL)";
	
	static final String SQL_DROP_TABLE =
			"drop table if exists staff";
	
	Context context;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	
	public DBAdapter(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}
	
	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//Log.i(TAG, "running onCreate()");
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//Log.i(TAG, "running onUpgrade()");
			Log.i(TAG, "upgrading database from version " + oldVersion
					   + " to " + newVersion);
			db.execSQL(SQL_DROP_TABLE);
			onCreate(db);
		}
		
	}
	
	public DBAdapter open() {
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public boolean insertPerson(String name, double income,
								double bonus, String phone) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate = sDateFormat.format(new java.util.Date());
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_INCOME, income);
		values.put(COLUMN_BONUS, bonus);
		values.put(COLUMN_PHONE, phone);
		values.put(COLUMN_CREATED_AT, strDate);
		return db.insert(DATABASE_TABLE, null, values) > 0;
	}
	
	public boolean updatePerson(String name, double income,
								double bonus, String phone) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate = sDateFormat.format(new java.util.Date());
	    
		ContentValues values = new ContentValues();
		values.put(COLUMN_INCOME, income);
		values.put(COLUMN_BONUS, bonus);
		values.put(COLUMN_PHONE, phone);
		values.put(COLUMN_CREATED_AT, strDate);
		return db.update(DATABASE_TABLE, values, COLUMN_NAME + "=?", new String[] {name}) > 0;
	}
	
	public Cursor getAllPeople()  {
		String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_INCOME, COLUMN_BONUS, COLUMN_PHONE};
		return db.query(DATABASE_TABLE, columns, null, null, null, null, null);
	}
	
	public Cursor getPerson(String name) {
		String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_INCOME, COLUMN_BONUS, COLUMN_PHONE};
		Cursor cursor = db.query(true, DATABASE_TABLE, columns,
				COLUMN_NAME + "=?", new String[] {name}, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public boolean removePerson(String name) {
		return db.delete(DATABASE_TABLE, COLUMN_NAME + "=?", new String[] {name}) > 0;
	}
	
	public boolean removeAllPerson() {
		return db.delete(DATABASE_TABLE, null, null) > 0;
	}
	
}
