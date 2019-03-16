package com.semicolon.moviehub.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.moviehub.R;
import com.semicolon.moviehub.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 23/04/2016.
 */
public class NewArivalVideoAdapter extends PagerAdapter {


	private ArrayList<Video> imageModelArrayList;
	private LayoutInflater inflater;
	private Context context;


	public NewArivalVideoAdapter(Context context, ArrayList<Video> imageModelArrayList) {
		this.context = context;
		this.imageModelArrayList = imageModelArrayList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return imageModelArrayList.size();
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.new_movies_list_item, view, false);

		assert imageLayout != null;
		final ImageView imageView = imageLayout.findViewById(R.id.poster);
		final TextView titleView = imageLayout.findViewById(R.id.title);
		titleView.setText(imageModelArrayList.get(position).title);
		Picasso.get().load(imageModelArrayList.get(position).posterUrl)
				.fit().centerCrop().placeholder(R.drawable.ic_launcher_background)
				.error(R.drawable.ic_launcher_foreground).into(imageView);

		view.addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}


}