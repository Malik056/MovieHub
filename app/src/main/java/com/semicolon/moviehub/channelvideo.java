package com.semicolon.moviehub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.semicolon.moviehub.adapters.VideoAdapter;
import com.semicolon.moviehub.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class channelvideo extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
    GestureDetector gestureDetector;
    ArrayList<Video> videos;
    RecyclerView rv;
    VideoAdapter adapter;
    RequestQueue requestQueue;
    String url="https://www.googleapis.com/youtube/v3/search?key=AIzaSyAywyVuq6CXu-Zezjgz_n67gmxxaJ_w4cY&channelId=UCMmpLL2ucRHAXbNHiCPyIyg&part=snippet,id&order=date&maxResults=20&eventType=live&type=video";
    // String url="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCMmpLL2ucRHAXbNHiCPyIyg&eventType=live&type=video&key=AIzaSyAywyVuq6CXu-Zezjgz_n67gmxxaJ_w4cY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelvideo);

        requestQueue= Volley.newRequestQueue(this);
        videos=new ArrayList<>();

        adapter=new VideoAdapter(videos, R.layout.video_item);
        rv=(RecyclerView) findViewById(R.id.vRecyclerview);
        rv.addOnItemTouchListener(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        retrieveVideos();






        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //      Toast.makeText(c,"onSingleTap",Toast.LENGTH_SHORT).show();

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null)
                {
                    int pos=rv.getChildAdapterPosition(child);
                    Intent intent=new Intent(getApplicationContext(), LiveStreaming.class);
                    intent.putExtra("vId",videos.get(pos).getVideoId());
                    startActivity(intent);

                }
                return true;
            }
        }

        );




    }


    public void retrieveVideos()
    {
        JSONArray json1=new JSONArray();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                        Toast.makeText(getApplicationContext(),"Video Id:"+ vId,Toast.LENGTH_LONG).show();

                    }

                }
                catch (JSONException ex)
                {
                    Toast.makeText(channelvideo.this, "Catch()"+ex.toString(), Toast.LENGTH_SHORT).show();

                }
            }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error occured: "+error.toString(), Toast.LENGTH_SHORT).show();
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
