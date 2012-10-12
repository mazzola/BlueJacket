package com.bluejacket.retailapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExploreFragment extends Fragment {
	private ListView list;
	private MyArrayAdapter mArrayAdapter;
	private AlertDialog dialog;
	
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
	
	public ExploreFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View exploreView = inflater.inflate(R.layout.fragment_explore, container, false);

		// Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
		
		mArrayAdapter = new MyArrayAdapter(getActivity(), R.layout.list_item, DUMMY_DATA);
		list = (ListView) exploreView.findViewById(R.id.listView);
		list.setEmptyView(progressBar);
		list.setAdapter(mArrayAdapter);
		
		final CharSequence[] items = {"Check in here", "Get directions"};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Wegmans");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	switch (item) {
				case 0:
					dialog.dismiss();
					break;
				case 1:
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					Uri.parse("http://maps.google.com/maps?daddr=Chili+%26+Wegmans+Market"));
					startActivity(intent);
					break;
				default:
					dialog.dismiss();	
					break;
				}
		    }
		});
		dialog = builder.create();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.show();
			}
		});
		
		
		return exploreView;
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
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
