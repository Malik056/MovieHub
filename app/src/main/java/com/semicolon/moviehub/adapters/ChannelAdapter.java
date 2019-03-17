package com.semicolon.moviehub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.moviehub.Viewholders.ChannelViewHolder;
import com.semicolon.moviehub.Viewholders.VideoViewHolder;
import com.semicolon.moviehub.models.ChannelInfo;
import com.semicolon.moviehub.models.Video;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    ArrayList<ChannelInfo> channels;
    int layout;

    public ChannelAdapter(ArrayList<ChannelInfo> chan, int layout)
    {
        this.channels=chan;
        this.layout=layout;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(this.layout,viewGroup,false);
        return new ChannelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder viewHolder, int pos) {
        if(channels!=null && viewHolder!=null)
        {
            viewHolder.setTitle(channels.get(pos).getName());
            viewHolder.setThumbnail(channels.get(pos).getMap());
        }

    }

    @Override
    public int getItemCount() {
        if(channels!=null)
            return channels.size();
        return 0;
    }
}

