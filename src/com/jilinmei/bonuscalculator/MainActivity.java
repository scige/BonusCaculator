package com.jilinmei.bonuscalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
	private static final String DEBUG_TAG = "Debug";

	//ArrayList<String> staffItems = null;
	//ArrayAdapter<String> adapter = null;
	
    List<Map<String,String>> staffItems = null;
    SimpleAdapter adapter = null;
        
	DBAdapter db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		ListView listView = (ListView)findViewById(R.id.staffListView);
		//staffItems = new ArrayList<String>();
		//adapter = new ArrayAdapter<String>(
		//		this, android.R.layout.simple_expandable_list_item_1, staffItems);
		
		staffItems = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(this, staffItems, R.layout.list_item,
									new String[] {"name", "income", "bonus"},
									new int[] {R.id.list_item_name, R.id.list_item_income, R.id.list_item_bonus});
		listView.setAdapter(adapter);
		
        db = new DBAdapter(this);
        db.open();
        
		listView.setOnItemClickListener(new MyItemClickListener());
    }
    
    public class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d(DEBUG_TAG, "position: " + position + ", id: " + id);
			Intent intent = new Intent(MainActivity.this, DetailActivity.class);
			Map<String, String> itemMap = staffItems.get(position);
			intent.putExtra("PERSON_NAME", itemMap.get("name"));
			startActivity(intent);
		}
    	
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_add:
			Intent intent = new Intent(MainActivity.this, EditActivity.class);
			startActivity(intent);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
	}
    
	@Override
	protected void onResume() {
		staffItems.clear();
		Cursor cursor = db.getAllPeople();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			String name = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_NAME));
			double income = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_INCOME));
			double bonus = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_BONUS));
			String phone = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_PHONE));
			Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("name", name);
			itemMap.put("income", String.valueOf(income));
			itemMap.put("bonus", String.valueOf(bonus));
			staffItems.add(itemMap);
		}
		adapter.notifyDataSetChanged();
		
		super.onResume();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
