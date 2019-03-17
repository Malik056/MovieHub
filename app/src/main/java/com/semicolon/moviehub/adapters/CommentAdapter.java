package com.semicolon.moviehub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.moviehub.R;
import com.semicolon.moviehub.holders.CommentHolder;
import com.semicolon.moviehub.model.Comment;

import java.util.ArrayList;

/**
 * Created by Taha Malik on 17/03/2019.
 **/
public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

	private ArrayList<Comment> mComments;

	public CommentAdapter(@NonNull ArrayList<Comment> pComments) {
		mComments = pComments;
	}

	@NonNull
	@Override
	public CommentHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
		View view = LayoutInflater.from(pViewGroup.getContext())
				.inflate(R.layout.comment_item, null);
		return new CommentHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CommentHolder pCommentHolder, int pI) {
		Comment c = mComments.get(pI);

		pCommentHolder.username.setText(c.username);
		pCommentHolder.comment.setText(c.comment);

	}

	@Override
	public int getItemCount() {
		return mComments.size();
	}
}
