package com.semicolon.moviehub.Viewholders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.moviehub.R;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ImageView thumbnail;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.vTitle);
        thumbnail = (ImageView) itemView.findViewById(R.id.vThumbnail);

    }

    public void setTitle(String text)
    {
        title.setText(text);
    }

    public void setThumbnail(Bitmap map) {
        thumbnail.setImageBitmap(map);
    }

}
