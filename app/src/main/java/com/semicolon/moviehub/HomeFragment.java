package com.semicolon.moviehub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.moviehub.adapters.NewArivalVideoAdapter;
import com.semicolon.moviehub.adapters.VideoListAdapter;
import com.semicolon.moviehub.model.Video;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

	public HomeFragment() {
		// Required empty public constructor
	}


	private ArrayList<Video> imageModelArrayList;
	private ViewPager mPager;
	private static int currentPage = 0;
	private static int NUM_PAGES = 0;
	private RecyclerView recently_watched;
	private RecyclerView recommended_videos;
	private ArrayList<Video> recent;
	private ArrayList<Video> recommended;
	private View view;

	// TODO: Rename and change types and number of parameters
	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_home, container, false);
		populateList();
		recently_watched = view.findViewById(R.id.recent_videos);
		recommended_videos = view.findViewById(R.id.recommended_videos);
		init();

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
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

		mPager = view.findViewById(R.id.pager);
		mPager.setAdapter(new NewArivalVideoAdapter(getContext(), imageModelArrayList));
		recently_watched.setLayoutManager(new LinearLayoutManager(getContext()
				, LinearLayoutManager.HORIZONTAL, false));
		LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getContext()
				, LinearLayoutManager.HORIZONTAL, false);
		recommended_videos.setLayoutManager(lLinearLayoutManager);
		recently_watched.setAdapter(new VideoListAdapter(recent));
		recommended_videos.setAdapter(new VideoListAdapter(recommended));

		final float density = getResources().getDisplayMetrics().density;


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