package com.semicolon.moviehub.Viewholders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.moviehub.R;

public class ChannelViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView thumbnail;

    public ChannelViewHolder(@NonNull View itemView) {

        super(itemView);
        title = (TextView) itemView.findViewById(R.id.cTitle);
        thumbnail = (ImageView) itemView.findViewById(R.id.cThumbnail);

    }

    public void setTitle(String text)
    {
        title.setText(text);
    }

    public void setThumbnail(Bitmap map) {
        thumbnail.setImageBitmap(map);
    }

}

