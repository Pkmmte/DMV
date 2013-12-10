package com.pk.dmv.activity;

import com.pk.dmv.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityPracticeTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_practice_test, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				Exit();
				return true;
			case R.id.action_info:
				// TODO Open information dialog
				Toast.makeText(this, "Information stuff goes here.", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Exit();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	private void Exit()
	{
		finish();
		overridePendingTransition(R.anim.fslide_left_in, R.anim.fslide_right_out);
	}
}
