package com.semicolon.moviehub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.moviehub.Viewholders.VideoViewHolder;
import com.semicolon.moviehub.models.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    ArrayList<Video> videos;
    int layout;

    public VideoAdapter(ArrayList<Video> videos, int layout)
    {
        this.videos=videos;
        this.layout=layout;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(this.layout,viewGroup,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder viewHolder, int pos) {
        if(videos!=null && viewHolder!=null)
        {
            viewHolder.setTitle(videos.get(pos).getTitle());
            viewHolder.setThumbnail(videos.get(pos).getMap());
        }

    }

    @Override
    public int getItemCount() {
        if(videos!=null)
            return videos.size();
        return 0;
    }
}
