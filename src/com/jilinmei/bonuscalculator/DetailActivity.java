package com.jilinmei.bonuscalculator;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends Activity
							implements RemovePersonDialogFragment.NoticeDialogListener {

	private static final String DEBUG_TAG = "Debug";
	
	DBAdapter db;
	String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
        db = new DBAdapter(this);
        db.open();
        
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("PERSON_NAME");
	}
	
	public void editPerson_Clicked(View view) {
		TextView nameText = (TextView)findViewById(R.id.nameText);
		String name = nameText.getText().toString();
		Intent intent = new Intent(DetailActivity.this, EditActivity.class);
		intent.putExtra("PERSON_NAME", name);
		startActivity(intent);
	}
	
	public void removePerson_Clicked(View view) {
		DialogFragment dialogFragment = new RemovePersonDialogFragment();
		dialogFragment.show(getFragmentManager().beginTransaction(), "removeperson");
	}
	
	public void backButton_Clicked(View view) {
		finish();
	}
	
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
		TextView nameText = (TextView)findViewById(R.id.nameText);
		String name = nameText.getText().toString();
		db.removePerson(name);
		finish();
		Toast.makeText(this, "员工 [" + name + "] 已经被删除", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
    
	@Override
	protected void onResume() {
		Cursor cursor = db.getPerson(name);
		double income = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_INCOME));
		double bonus = cursor.getDouble(cursor.getColumnIndex(DBAdapter.COLUMN_BONUS));
		String phone = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_PHONE));
		
		TextView nameText = (TextView)findViewById(R.id.nameText);
		nameText.setText(name);
		TextView incomeText = (TextView)findViewById(R.id.incomeText);
		incomeText.setText(String.valueOf(income));
		TextView bonusText = (TextView)findViewById(R.id.bonusText);
		bonusText.setText(String.valueOf(bonus));
		TextView phoneText = (TextView)findViewById(R.id.phoneText);
		phoneText.setText(phone);
		
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
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
