package com.semicolon.moviehub;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.semicolon.moviehub.R;
import com.semicolon.moviehub.adapters.VideoAdapter;
import com.semicolon.moviehub.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelVideoFragment extends Fragment implements RecyclerView.OnItemTouchListener{


	public ChannelVideoFragment() {
		// Required empty public constructor
	}

	GestureDetector gestureDetector;
	ArrayList<Video> videos;
	RecyclerView rv;
	VideoAdapter adapter;
	RequestQueue requestQueue;
	ArrayList<String> channelIDs;
	String channelURL="https://www.googleapis.com/youtube/v3/channels?part=snippet&forUsername=ary+news&key=AIzaSyAywyVuq6CXu-Zezjgz_n67gmxxaJ_w4cY";
	String videoURL;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_channelvideo, null);


		requestQueue= Volley.newRequestQueue(Objects.requireNonNull(getContext()));
		videos=new ArrayList<>();

		adapter=new VideoAdapter(videos, R.layout.video_item);
		rv=(RecyclerView) view.findViewById(R.id.vRecyclerview);
		rv.addOnItemTouchListener(this);

		rv.setLayoutManager(new LinearLayoutManager(getContext()));
		rv.setItemAnimator(new DefaultItemAnimator());
		rv.setAdapter(adapter);

		channelIDs=new ArrayList<>();

		channelIDs.add("UCNye-wNBqNL5ZzHSJj3l8Bg");
		channelIDs.add("UCuUocUAnPTUkwGtC8GuNKow");
		channelIDs.add("UCMmpLL2ucRHAXbNHiCPyIyg");
		channelIDs.add("UCTur7oM6mLL0rM2k0znuZpQ");

		for(int i=0;i<channelIDs.size();i++){
			retrieveVideos(channelIDs.get(i));
		}

		gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
		{
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				//      Toast.makeText(c,"onSingleTap",Toast.LENGTH_SHORT).show();

				View child = rv.findChildViewUnder(e.getX(), e.getY());
				if(child != null)
				{
					int pos=rv.getChildAdapterPosition(child);
					Intent intent=new Intent(getContext(), LiveStreaming.class);
					intent.putExtra("vId",videos.get(pos).getVideoId());
					startActivity(intent);

				}
				return true;
			}
		}

		);


		return view;
	}

	public void retrieveVideos(String cId)
	{
		JSONArray json1=new JSONArray();
		videoURL="https://www.googleapis.com/youtube/v3/search?key=AIzaSyAywyVuq6CXu-Zezjgz_n67gmxxaJ_w4cY&channelId="+cId+"&part=snippet,id&order=date&maxResults=20&eventType=live&type=video";
		StringRequest stringRequest=new StringRequest(Request.Method.GET, videoURL, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					//Toast.makeText(getApplicationContext(),"Total recieved:"+ jsonArray.length(),Toast.LENGTH_LONG).show();
					for(int i=0;i<jsonArray.length();i++)
					{
						JSONObject jsonObject1=jsonArray.getJSONObject(i);
						JSONObject videoId=jsonObject1.getJSONObject("id");
						JSONObject snippet=jsonObject1.getJSONObject("snippet");
						JSONObject defaul=snippet.getJSONObject("thumbnails").getJSONObject("medium");
						String vId=videoId.getString("videoId");

						Log.d("TAG","Got Something"+vId);
						final Video v=new Video(vId,snippet.getString("title"),snippet.getString("description"),defaul.getString("url"));


						ImageRequest imageRequest = new ImageRequest(v.getUrl(),
								new Response.Listener<Bitmap>() {
									@Override
									public void onResponse(Bitmap bitmap) {
										v.setMap(bitmap);
										videos.add(v);
										adapter.notifyDataSetChanged();
									}
								}, 0, 0, null,
								new Response.ErrorListener() {
									public void onErrorResponse(VolleyError error) {
										//mImageView.setImageResource(R.drawable.image_load_error);
									}
								});
						requestQueue.add(imageRequest);
						Toast.makeText(getContext(),"Video Id:"+ vId,Toast.LENGTH_LONG).show();

					}

				}
				catch (JSONException ex)
				{
					Toast.makeText(getContext(), "Catch()"+ex.toString(), Toast.LENGTH_SHORT).show();

				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getContext(), "Error occured: "+error.toString(), Toast.LENGTH_SHORT).show();
			}
		});

		requestQueue.add(stringRequest);


	}


	@Override
	public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent e) {

		gestureDetector.onTouchEvent(e);
		return false;
	}

	@Override
	public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent e) {

	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean b) {

	}
}
