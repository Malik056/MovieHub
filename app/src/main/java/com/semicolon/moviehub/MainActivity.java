package com.semicolon.moviehub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Config;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	private TextView mTextMessage;
	private ViewPager lViewPager;
	Activity activity=this;

    FloatingActionButton floatingActionButton;


    public void showChangeLanguageDialogue()
    {
        final String [] listItems={"English","français"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(getResources().getString(R.string.change_language));
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("fr");
                    recreate();

                }
                dialogInterface.dismiss();
            }

        });
        AlertDialog mDialogue=mBuilder.create();
        mDialogue.show();
    }


    public static final String uploader = "Uploader";
	public static final String admin = "admin";
	public static final String premium_user = "premium";
	public static final String normal_user= "user";



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
				case R.id.search_video:
					lViewPager.setCurrentItem(2);
			}
			return false;
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		String userType = getIntent().getStringExtra("user");
		if(userType == null) userType = normal_user;
		if(userType.equals(normal_user))
		{
			getMenuInflater().inflate(R.menu.user_menu, menu);
		}
		else if(userType.equals(uploader))
		{
			getMenuInflater().inflate(R.menu.uploader_admin_menu, menu);
		}
		else if(userType.equals(premium_user))
		{
			getMenuInflater().inflate(R.menu.premium_user_menu, menu);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId())
		{
			case R.id.add_video:
				Intent lIntent = new Intent(getApplicationContext(),UploadActivity.class);
				startActivity(lIntent);
				break;
			case R.id.change_lang:
				showChangeLanguageDialogue();
				break;
			case R.id.go_premium:
				item.setTitle(R.string.become_uploader);
				break;
			case R.id.become_uploader:
				item.setVisible(false);
				break;
		}


		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Language Code
        loadLocale();
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setTitle(getResources().getString(R.string.app_name));
        }
//		mTextMessage = findViewById(R.id.message);
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
			else if(pI == 1) {
				return new ChannelVideoFragment();
			}
			else
			{
				return new HomeFragment();
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	}

	void setLocale(String lang)
	{
		Locale locale=new Locale(lang);
		Locale.setDefault(locale);
		Configuration config=new Configuration();
		config.setLocale(locale);

		getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
		SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
		editor.putString("My_Lang",lang);
		editor.apply();



	}

	public void loadLocale()
	{
		SharedPreferences preferences=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		String lang=preferences.getString("My_Lang","");
		setLocale(lang);
	}

}
