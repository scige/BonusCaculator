package com.jilinmei.bonuscalculator;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

	private static final String DEBUG_TAG = "Debug";
	
	boolean isEdit = false;
	
	DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
        db = new DBAdapter(this);
        db.open();
        
        Bundle bundle = getIntent().getExtras();
        String name;
        try {
        	name = bundle.getString("PERSON_NAME");
        }
        catch (Exception e) {
        	name = "";
        	Log.i(DEBUG_TAG, "running in adding person mode");
        }
        
        if (name == "") {
        	isEdit = false;
        	return;
        }
        
        isEdit = true;
		Cursor cursor = db.getPerson(name);
		double income = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_INCOME));
		double bonus = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_BONUS));
		String phone = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_PHONE));
		
		EditText nameEdit = (EditText)findViewById(R.id.nameEditText);
		nameEdit.setText(name);
		EditText incomeEdit = (EditText)findViewById(R.id.incomeEditText);
		incomeEdit.setText(String.valueOf(income));
		EditText bonusEdit = (EditText)findViewById(R.id.bonusEditText);
		bonusEdit.setText(String.valueOf(bonus));
		EditText phoneEdit = (EditText)findViewById(R.id.phoneEditText);
		phoneEdit.setText(phone);
	}
	
	public void newPersonSave_Clicked(View view) {
		EditText nameEdit = (EditText)findViewById(R.id.nameEditText);
		String name = nameEdit.getText().toString();
		EditText incomeEdit = (EditText)findViewById(R.id.incomeEditText);
		String incomeStr = incomeEdit.getText().toString();
		double income = Double.parseDouble(incomeStr);
		EditText bonusEdit = (EditText)findViewById(R.id.bonusEditText);
		String bonusStr = bonusEdit.getText().toString();
		double bonus = Double.parseDouble(bonusStr);
		EditText phoneEdit = (EditText)findViewById(R.id.phoneEditText);
		String phone = phoneEdit.getText().toString();
		
		if (isEdit) {
			Log.d(DEBUG_TAG, "update");
			db.updatePerson(name, income, bonus, phone);
		}
		else {
			Log.d(DEBUG_TAG, "insert");
			db.insertPerson(name, income, bonus, phone);
		}
	    
	    finish();
	}
	
	public void newPersonCancel_Clicked(View view) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

}
