package com.semicolon.moviehub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semicolon.moviehub.adapters.NewArivalVideoAdapter;
import com.semicolon.moviehub.adapters.VideoListAdapter;
import com.semicolon.moviehub.model.Video;
import com.semicolon.moviehub.models.User;

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
	private static String user = User.viewer;

	public static HomeFragment newInstance(String user) {
		HomeFragment.user = user;
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
		recently_watched = view.findViewById(R.id.recent_videos);
		recommended_videos = view.findViewById(R.id.recommended_videos);
		imageModelArrayList = new ArrayList<>();
		recent = new ArrayList<>();
		recommended = new ArrayList<>();
		init();
		populateList(recently_watched.getAdapter(), recommended_videos.getAdapter(), mPager.getAdapter());

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

	private void populateList(final RecyclerView.Adapter pAdapter1, final RecyclerView.Adapter pAdapter2, final PagerAdapter pPagerAdapter){

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Videos");

		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {
				Iterable<DataSnapshot> lDataSnapshots = pDataSnapshot.getChildren();
				int i = 0;
				for(DataSnapshot d : lDataSnapshots)
				{
					Video lVideo = new Video();
					lVideo.id = d.getKey();
					lVideo.uploader = (String) d.child("uploader").getValue();
					lVideo.videoUrl = (String) d.child("url").getValue();
					lVideo.description = (String) d.child("description").getValue();
					lVideo.title= (String) d.child("title").getValue();
					lVideo.type = (String) d.child("type").getValue();
					lVideo.type = (String) d.child("genre").getValue();

					if(user.equals(User.viewer) && lVideo.type.equals(Video.public_video)) {
						if (i < 6)
							imageModelArrayList.add(lVideo);

						if (i % 3 == 0) {
							recent.add(lVideo);
						}

						recommended.add(lVideo);
					}
					else if(user.equals(User.uploader) || user.equals(User.premium) || user.equals(User.admin))
					{
						if (i < 6)
							imageModelArrayList.add(lVideo);

						if (i % 3 == 0) {
							recent.add(lVideo);
						}

						recommended.add(lVideo);

					}
					i++;
				}
				if(pAdapter1 != null)
					pAdapter1.notifyDataSetChanged();
				if(pAdapter2 != null)
					pAdapter2.notifyDataSetChanged();
				if(pPagerAdapter != null)
					pPagerAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError pDatabaseError) {

			}
		});


		for(int i = 0; i < 2; i++){
			Video imageModel = new Video();
			imageModelArrayList.add(imageModel);
		}

		for(int i = 0; i < 2; i++){
			Video imageModel = new Video();
			recommended.add(imageModel);
		}

		for(int i = 0; i < 2; i++){
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

	}
}