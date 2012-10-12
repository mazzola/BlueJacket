package com.bluejacket.retailapp;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class CheckinActivity extends MapActivity {
	private MapView map;
	private MapController controller;
	private ListView list;
	private MyArrayAdapter mArrayAdapter;
	
	private static String[][] DUMMY_DATA = new String[][] {
			{"Walmart", "3010 Chili Ave", "0.5 mi"},
			{"Wilson Farms", "25 Chestnut Rd", "0.4 mi"},
			{"Target", "607 Coldwater Rd", "0.6 mi"},
			{"Walmart", "607 Coldwater Rd", "0.6 mi"},
			{"Wegmans", "32 Pine Rd", "0.8 mi"},
			{"Target", "7 Buffalo St", "1.6 mi"},	
			{"Mom and Pop", "123 Smith St", "3.2 mi"},
			{"Kmart", "6456 Exchange St", "2.5 mi"}
		};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		initMapView();
		initMyLocation();
		
		// Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
        
        mArrayAdapter = new MyArrayAdapter(this, R.layout.list_item, DUMMY_DATA);
		list = (ListView) findViewById(R.id.listView);
		list.setEmptyView(progressBar);
		list.setAdapter(mArrayAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				MainActivity.USER_CHECKED_IN = true;
				finish();
			}
		});
		
		// Must add the progress bar to the root of the layout
		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		root.addView(progressBar);
	}
	
	/** Find and initialize the map view. */
	private void initMapView() {
		map = (MapView) findViewById(R.id.mapview);
		controller = map.getController();
	}
	
	/** Start tracking the position on the map. */
	private void initMyLocation() {
		final MyLocationOverlay overlay = new MyLocationOverlay(this, map);
		overlay.enableMyLocation();
		overlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				controller.setZoom(17);
				controller.animateTo(overlay.getMyLocation());
			}
		});
		map.getOverlays().add(overlay);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		default:
			return false;
		}
	}
	
	public class MyArrayAdapter extends ArrayAdapter<String[]> {
		private final Context context;
		private final String[][] values;
		
		public MyArrayAdapter(Context context, int textViewResourceId,
				String[][] objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			values = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View rowItem = inflater.inflate(R.layout.list_item, parent, false);
			TextView storeName = (TextView) rowItem.findViewById(R.id.storeName);
			TextView storeDistance = (TextView) rowItem.findViewById(R.id.storeDistance);
			TextView storeAddress = (TextView) rowItem.findViewById(R.id.storeAddress);
			
			storeName.setText(values[position][0]);
			storeAddress.setText(values[position][1]);
			storeDistance.setText(values[position][2]);
			return rowItem;
		}
		
	}
}
