package com.semicolon.moviehub.Viewholders;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.semicolon.moviehub.R;

public class GenreViewholder extends RecyclerView.ViewHolder {

    private TextView genreName;
    private Boolean checked;
    private CheckBox checkBox;

    public GenreViewholder(@NonNull View itemView, Boolean check) {
        super(itemView);
        genreName = itemView.findViewById(R.id.genreItem);
        checked = check;
        checkBox = itemView.findViewById(R.id.genreCheckbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            itemView.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    if(getChecked())
                        setChecked(false);
                    else
                        setChecked(true);
                    return false;
                }
            });
        }
    }

    public void setText(String text)
    {
        genreName.setText(text);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
        checkBox.setChecked(checked);
    }
}
