package com.semicolon.moviehub.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.semicolon.moviehub.R;

/**
 * Created by Taha Malik on 16/03/2019.
 **/
public class CommentHolder extends RecyclerView.ViewHolder {

	public TextView username;
	public TextView comment;


	public CommentHolder(@NonNull View itemView) {
		super(itemView);
		username = itemView.findViewById(R.id.username);
		comment = itemView.findViewById(R.id.comment);
	}
}
