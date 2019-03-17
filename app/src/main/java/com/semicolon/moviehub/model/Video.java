package com.semicolon.moviehub.model;


import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Taha Malik on 16/03/2019.
 **/
public class Video {

	public final static String public_video = "Public";
	public final static String private_video = "Special";

	public String type = "public";
	public String title = "Dummy Video";
	public String uploader = "dummy";
	public String id = "none";
	public String description = "Nothing o describe here";
//	public String posterUrl = "https://cloud.addictivetips.com/wp-content/uploads/2012/05/Gallery-for-Android-SlideShow.jpg";
	public String posterUrl = "https://i.pinimg.com/originals/8b/a7/cf/8ba7cf0af6ec014b9272c8f6d7f7fc92.jpg";
	public String videoUrl="https://sample-videos.com/video123/mp4/240/big_buck_bunny_240p_30mb.mp4";
	public String genre;
	public Video() {
	}

	public Video(String pType, String pTitle, String pUploader, String pId, String pDescription, String pPosterUrl, String pVideoUrl, String genre) {

		this.genre = genre;
		type = pType;
		title = pTitle;
		uploader = pUploader;
		id = pId;
		description = pDescription;
		posterUrl = pPosterUrl;
		videoUrl = pVideoUrl;
	}
}
