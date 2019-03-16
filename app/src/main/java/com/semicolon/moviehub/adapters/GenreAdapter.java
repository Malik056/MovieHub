package com.semicolon.moviehub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.moviehub.R;
import com.semicolon.moviehub.Viewholders.GenreViewholder;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewholder> {
    private ArrayList<String> genres;
    private ArrayList<Boolean> checkedList;
    public GenreAdapter(ArrayList<String> genres) {
        this.genres = genres;
        checkedList = new ArrayList<>();
    }

    public ArrayList<Boolean> getCheckedList()
    {
        return checkedList;
    }

    @NonNull
    @Override
    public GenreViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_listview, viewGroup,false);
        checkedList.add(false);
        return new GenreViewholder(view, checkedList.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewholder genreViewholder, int i) {
        genreViewholder.setText(genres.get(i));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

}
