package com.bluejacket.retailapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    public static boolean USER_CHECKED_IN = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
        // We can also use ActionBar.Tab#select() to do this if we have a reference to the
        // Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_checkin:
			Intent checkinIntent = new Intent(getBaseContext(), CheckinActivity.class);
			checkinIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(checkinIntent);
			return true;

		default:
			return false;
		}
    }
    
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	switch (i) {
			case 0:
				return new ExploreFragment();
			case 1:
				return new TaskSectionFragment();
			case 2:
				return new ProfileSectionFragment();
			default:
				return null;
			}
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.title_section1).toUpperCase();
                case 1: return getString(R.string.title_section2).toUpperCase();
                case 2: return getString(R.string.title_section3).toUpperCase();
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }
    
    /**
     * A dummy Task fragment representing the tasks a rep has to complete
     */
    public static class TaskSectionFragment extends Fragment {
    	public TaskSectionFragment() {
    	}
    	
    	@Override
    	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		View taskView = inflater.inflate(R.layout.fragment_tasks, container, false);
    		
    		ExpandableListView list1 = (ExpandableListView) taskView.findViewById(R.id.expandableListView1);
    		final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String,String>>();
    		HashMap<String, String> task1 = new HashMap<String, String>();
    		task1.put("name", "Lysol");
    		HashMap<String, String> task2 = new HashMap<String, String>();
    		task2.put("name", "Franks Red Hot");
    		headerData.add(task1);
    		headerData.add(task2);
    		
    		final ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<ArrayList<HashMap<String, String>>>();
    		final ArrayList<HashMap<String, String>> group1data = new ArrayList<HashMap<String,String>>();
    		final ArrayList<HashMap<String, String>> group2data = new ArrayList<HashMap<String,String>>();
    		childData.add(group1data);
    		childData.add(group2data);
    		
    		// Set up some sample data in both groups
    	    for( int i=0; i<2; ++i) {
    	        final HashMap<String, String> map = new HashMap<String,String>();
    	        map.put("name", "Child " + i );
    	        ( i%2==0 ? group1data : group2data ).add(map);
    	    }
    		
    		list1.setAdapter(new SimpleExpandableListAdapter(getActivity(), headerData, android.R.layout.simple_expandable_list_item_1, new String[] {"name"}, new int[] { android.R.id.text1 }, childData, 0, null, new int[] {})
    				{	
    					@Override
    					public View newChildView(boolean isLastChild,
    							ViewGroup parent) {
    						return inflater.inflate(R.layout.expandable_list_child, null, false);
    					}
    				});
    		
    		return taskView;
    	}
    }
    
    /**
     * A dummy task fragment representing the profile of a rep
     */
    public static class ProfileSectionFragment extends Fragment {
    	public ProfileSectionFragment() {
    	}
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		View profileView = inflater.inflate(R.layout.fragment_profile, container, false);
    		return profileView;
    	}
    }
}
