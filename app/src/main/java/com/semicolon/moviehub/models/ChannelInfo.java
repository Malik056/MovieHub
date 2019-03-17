package com.semicolon.moviehub.models;

import android.graphics.Bitmap;

public class ChannelInfo {

    String name,channelId;
    Bitmap map;

    public Bitmap getMap() {
        return map;
    }

    public void setMap(Bitmap map) {
        this.map = map;
    }

    public ChannelInfo()
    {

    }
    public ChannelInfo(String name, String channelId,Bitmap map) {
        this.name = name;
        this.channelId = channelId;
        this.map=map;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
