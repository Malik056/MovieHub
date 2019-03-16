package com.semicolon.moviehub.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.moviehub.R;

/**
 * Created by Taha Malik on 16/03/2019.
 **/
public class VideoHolder extends RecyclerView.ViewHolder {

	public ImageView poster;
	public TextView title;
	public View parent;

	public VideoHolder(@NonNull View itemView) {
		super(itemView);
		parent = itemView;
		poster = itemView.findViewById(R.id.poster);
		title = itemView.findViewById(R.id.title);

		itemView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View pView) {
			int pos = getAdapterPosition();
		}
	});
}
}
