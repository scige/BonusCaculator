package com.jilinmei.bonuscalculator;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

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
        
        if (name.equals("")) {
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
		boolean isDataOK = true;
		EditText nameEdit = (EditText)findViewById(R.id.nameEditText);
		String name = nameEdit.getText().toString();
		EditText incomeEdit = (EditText)findViewById(R.id.incomeEditText);
		String incomeStr = incomeEdit.getText().toString();
		double income = 0.0;
		try {
			income = Double.parseDouble(incomeStr);
		}
		catch (Exception ex) {
			//isDataOK = false;		允许输入为空
			income = 0.0;
		}
		EditText bonusEdit = (EditText)findViewById(R.id.bonusEditText);
		String bonusStr = bonusEdit.getText().toString();
		double bonus = 0.0;
		try {
			bonus = Double.parseDouble(bonusStr);
		}
		catch (Exception ex) {
			//isDataOK = false;		允许输入为空
			bonus = 0.0;
		}
		EditText phoneEdit = (EditText)findViewById(R.id.phoneEditText);
		String phone = phoneEdit.getText().toString();
		
		if (isDataOK && name.equals(""))
			isDataOK = false;
		if (isDataOK && income < 0.0)
			isDataOK = false;
		if (isDataOK && bonus < 0.0)
			isDataOK = false;
	    
		if (isDataOK) {
			if (isEdit) {
				Log.d(DEBUG_TAG, "update");
				db.updatePerson(name, income, bonus, phone);
			}
			else {
				Log.d(DEBUG_TAG, "insert");
				Cursor cursor = db.getPerson(name);
				if (cursor.getCount() > 0) {
					Toast.makeText(this, "员工 [" + name + "] 已经存在，请重新输入", Toast.LENGTH_SHORT).show();
					return;
				}
				db.insertPerson(name, income, bonus, phone);
			}
			finish();
		}
		else {
			Toast.makeText(this, "输入员工信息有错误，请重新输入", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void newPersonCancel_Clicked(View view) {
		finish();
	}
	
	public void backButton_Clicked(View view) {
		finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

}
