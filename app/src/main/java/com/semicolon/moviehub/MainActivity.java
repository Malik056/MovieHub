package com.semicolon.moviehub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private TextView mTextMessage;
	private ViewPager lViewPager;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:

					lViewPager.setCurrentItem(0);
					return true;
				case R.id.navigation_dashboard:
					lViewPager.setCurrentItem(1);
					return true;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextMessage = findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

		lViewPager = findViewById(R.id.container);
		lViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


	}

	private class PagerAdapter extends FragmentPagerAdapter{


		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pI) {
			if(pI == 0)
			{
				return HomeFragment.newInstance();
			}
			else {
				return new ChannelVideoFragment();
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

}
