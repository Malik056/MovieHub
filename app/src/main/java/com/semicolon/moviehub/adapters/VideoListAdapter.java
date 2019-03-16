package com.semicolon.moviehub.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.moviehub.R;
import com.semicolon.moviehub.VideoViewerActivity;
import com.semicolon.moviehub.holders.VideoHolder;
import com.semicolon.moviehub.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Taha Malik on 16/03/2019.
 **/
public class VideoListAdapter extends RecyclerView.Adapter<VideoHolder> {

	private ArrayList<Video> mVideos;
	public VideoListAdapter(@NonNull ArrayList<Video> pVideos)
	{
		mVideos = pVideos;
	}


	@NonNull
	@Override
	public VideoHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
		View lView = LayoutInflater.from(pViewGroup.getContext())
				.inflate(R.layout.horizontal_list_item, null);
		return new VideoHolder(lView);
	}

	@Override
	public void onBindViewHolder(@NonNull final VideoHolder pVideoHolder, int pI) {
		final Video v = mVideos.get(pI);
		ImageView poster = pVideoHolder.poster;
		pVideoHolder.parent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView) {
				Intent lIntent = new Intent(pView.getContext(), VideoViewerActivity.class);
				lIntent.putExtra("url", v.videoUrl);
				lIntent.putExtra("poster", v.posterUrl);
				lIntent.putExtra("title", v.title);
				lIntent.putExtra("ID", v.id);
				lIntent.putExtra("uploader", v.uploader);
				lIntent.putExtra("type", v.type);
				pView.getContext().startActivity(lIntent);
			}
		});

		Picasso.get().load(v.posterUrl).error(R.drawable.ic_launcher_background)
				.placeholder(R.drawable.ic_launcher_foreground)
				.into(poster);

		pVideoHolder.title.setText(v.title);

	}

	@Override
	public int getItemCount() {
		return mVideos.size();
	}
}

