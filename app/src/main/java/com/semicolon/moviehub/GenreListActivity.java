package com.semicolon.moviehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.semicolon.moviehub.adapters.GenreAdapter;

import java.util.ArrayList;

public class GenreListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        ArrayList<String> genres = new ArrayList<>();
        genres.add("Horror");
        genres.add("Comedy");
        genres.add("Thriller");
        genres.add("Family");

        recyclerView = findViewById(R.id.genreList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new GenreAdapter(genres);
        recyclerView.setAdapter(mAdapter);

        Button doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Boolean> checklist = new ArrayList<>();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user",MainActivity.normal_user);
                startActivity(intent);
            }
        });

    }
}
