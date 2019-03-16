package com.semicolon.moviehub.models;

import android.graphics.Bitmap;

public class Video {
    String videoId,title,description,url;
    Bitmap map;

    public Video() {
    }

    public Video(String videoId, String title, String description, String url) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getMap() {
        return map;
    }

    public void setMap(Bitmap map) {
        this.map = map;
    }
}
