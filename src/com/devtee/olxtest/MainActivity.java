package com.devtee.olxtest;

import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity{

	private Context context;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private PagerAdapter adapter;
	
	private List<String> urlList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new PagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
		
		urlList.add("https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&site=stackoverflow");
		urlList.add("https://api.stackexchange.com/2.2/questions/featured?order=desc&sort=activity&site=stackoverflow");
		urlList.add("https://api.stackexchange.com/2.2/questions?order=desc&sort=hot&site=stackoverflow");
		urlList.add("https://api.stackexchange.com/2.2/questions?order=desc&sort=week&site=stackoverflow");
		urlList.add("https://api.stackexchange.com/2.2/questions?order=desc&sort=month&site=stackoverflow");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_actions, menu);
	    
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
	    
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			Intent intent2 = new Intent(context, SearchActivity.class);
			intent2.putExtra(SearchActivity.TAG_QUERY, query);
			startActivity(intent2);
		}
	}

	private class PagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Interesting", "Featured", "Hot", "Week", "Month"};
		
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public Fragment getItem(int position) {
			ContentFragment fragment = new ContentFragment().newInstance(urlList.get(position));
			return fragment;
		}
		
	}
}
