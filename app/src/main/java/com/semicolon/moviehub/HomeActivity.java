package com.semicolon.moviehub;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.semicolon.moviehub.adapters.NewArivalVideoAdapter;
import com.semicolon.moviehub.adapters.VideoListAdapter;
import com.semicolon.moviehub.model.Video;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

	private ArrayList<Video> imageModelArrayList;
	private ViewPager mPager;
	private static int currentPage = 0;
	private static int NUM_PAGES = 0;
	private RecyclerView recently_watched;
	private RecyclerView recommended_videos;
	private ArrayList<Video> recent;
	private ArrayList<Video> recommended;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		populateList();
		recently_watched = findViewById(R.id.recent_videos);
		recommended_videos = findViewById(R.id.recommended_videos);
		init();
	}

	private void populateList(){

		imageModelArrayList = new ArrayList<>();
		recent = new ArrayList<>();
		recommended = new ArrayList<>();


		for(int i = 0; i < 6; i++){
			Video imageModel = new Video();
			imageModelArrayList.add(imageModel);
		}

		for(int i = 0; i < 6; i++){
			Video imageModel = new Video();
			recommended.add(imageModel);
		}

		for(int i = 0; i < 6; i++){
			Video imageModel = new Video();
			recent.add(imageModel);
		}
	}


	private void init() {

		mPager = findViewById(R.id.pager);
		mPager.setAdapter(new NewArivalVideoAdapter(HomeActivity.this,imageModelArrayList));
		recently_watched.setLayoutManager(new LinearLayoutManager(getApplicationContext()
		, LinearLayoutManager.HORIZONTAL, false));
		LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getApplicationContext()
				, LinearLayoutManager.HORIZONTAL, false);
		recommended_videos.setLayoutManager(lLinearLayoutManager);
		recently_watched.setAdapter(new VideoListAdapter(recent));
		recommended_videos.setAdapter(new VideoListAdapter(recommended));


//		mPager.setAdapter(new PagerAdapter() {
//			@Override
//			public int getCount() {
//				return imageModelArrayList.size();
//			}
//
//			@Override
//			public boolean isViewFromObject(@NonNull View pView, @NonNull Object pO) {
//				return false;
//			}
//
//			@NonNull
//			@Override
//			public Object instantiateItem(@NonNull ViewGroup container, int position) {
//				View lView = LayoutInflater.from(getApplicationContext())
//						.inflate(R.layout.new_movies_list_item, null);
//
//				ImageView lImageView = lView.findViewById(R.id.poster);
//				Picasso.get().load(imageModelArrayList.get(position).posterUrl).placeholder(R.drawable.ic_launcher_background)
//						.into(lImageView);
//				TextView lTextView = lView.findViewById(R.id.title);
//				assert lTextView != null && lImageView!=null;
//				container.addView(lView);
//
//				return container;
//			}
//		});

//		CirclePageIndicator indicator = (CirclePageIndicator)
//				findViewById(R.id.indicator);

//		indicator.setViewPager(mPager);

		final float density = getResources().getDisplayMetrics().density;



//Set circle indicator radius
//		indicator.setRadius(5 * density);

		NUM_PAGES =imageModelArrayList.size();

		// Auto start of viewpager
		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			public void run() {
				if (currentPage == NUM_PAGES) {
					currentPage = 0;
				}
				mPager.setCurrentItem(currentPage++, true);
			}
		};
		Timer swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(Update);
			}
		}, 3000, 3000);

		// Pager listener over indicator
//		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int position) {
//				currentPage = position;
//
//			}
//
//			@Override
//			public void onPageScrolled(int pos, float arg1, int arg2) {
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int pos) {
//
//			}
//		});

	}

}
