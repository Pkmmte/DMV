package com.pk.dmv;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import com.pk.dmv.adapter.MenuAdapter;

public class ActivityMain extends Activity {
	
	private ListView mList;
	private List<Integer> mItems;
	private MenuAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initMenu();
	}
	
	private void initMenu() {
		mList = (ListView) findViewById(R.id.ListView);
		mItems = new ArrayList<Integer>();
		
		for(int item = 0; item <= 6; item++)
			mItems.add(item);
		
		mAdapter = new MenuAdapter(this, mItems);
		mList.setAdapter(mAdapter);
	}
}
