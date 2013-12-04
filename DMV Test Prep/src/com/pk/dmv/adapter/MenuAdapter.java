package com.pk.dmv.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pk.dmv.R;

public class MenuAdapter extends BaseAdapter
{
	// Essential resources
	private Context context;
	private List<Integer> listItem;
	private Resources res;
	
	// Flag Constants
	public static final int PRACTICE_TEST = 0;
	public static final int TEST_OVERVIEW = 1;
	public static final int TEST_HISTORY = 2;
	public static final int FLASH_CARDS = 3;
	public static final int SIGNS = 4;
	public static final int HANDBOOK = 5;
	public static final int FIND_LOCAL = 6;
	public static final int CONTRIBUTE = 7;
	public static final int SETTINGS = 8;
	
	public MenuAdapter(Context context, List<Integer> listItem) {
		this.context = context;
		this.listItem = listItem;
		this.res = context.getResources();
	}
	
	public int getCount() {
		return listItem.size();
	}
	
	public Integer getItem(int position) {
		return listItem.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_main_menu_item, null);
			
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			holder.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		// Set text and icon
		switch(listItem.get(position)) {
			case PRACTICE_TEST:
				holder.txtTitle.setText(res.getString(R.string.practice_test));
				holder.txtDescription.setText(res.getString(R.string.practice_test_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case TEST_OVERVIEW:
				holder.txtTitle.setText(res.getString(R.string.test_overview));
				holder.txtDescription.setText(res.getString(R.string.test_overview_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case TEST_HISTORY:
				holder.txtTitle.setText(res.getString(R.string.test_history));
				holder.txtDescription.setText(res.getString(R.string.test_history_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case FLASH_CARDS:
				holder.txtTitle.setText(res.getString(R.string.flash_cards));
				holder.txtDescription.setText(res.getString(R.string.flash_cards_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case SIGNS:
				holder.txtTitle.setText(res.getString(R.string.signs));
				holder.txtDescription.setText(res.getString(R.string.signs_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case HANDBOOK:
				holder.txtTitle.setText(res.getString(R.string.handbook));
				holder.txtDescription.setText(res.getString(R.string.handbook_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case FIND_LOCAL:
				holder.txtTitle.setText(res.getString(R.string.find_local));
				holder.txtDescription.setText(res.getString(R.string.find_local_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case CONTRIBUTE:
				holder.txtTitle.setText(res.getString(R.string.contribute));
				holder.txtDescription.setText(res.getString(R.string.contribute_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
			case SETTINGS:
				holder.txtTitle.setText(res.getString(R.string.settings));
				holder.txtDescription.setText(res.getString(R.string.settings_description));
				holder.imgIcon.setImageResource(R.drawable.ic_launcher);
				break;
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		public TextView txtTitle;
		public TextView txtDescription;
		public ImageView imgIcon;
	}
}

