package com.semicolon.moviehub;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semicolon.moviehub.adapters.CommentAdapter;
import com.semicolon.moviehub.model.Comment;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoViewerActivity extends AppCompatActivity implements UniversalVideoView.VideoViewCallback{

	private static final String TAG = "MainActivity";
	private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
	private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

	UniversalVideoView mVideoView;
	UniversalMediaController mMediaController;

	View mBottomLayout;
	View mVideoLayout;
	TextView mStart;

	private int mSeekPosition;
	private int cachedHeight;
	private boolean isFullscreen;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_viewer_activity);


		Bundle lBundle = getIntent().getExtras();
		assert lBundle != null;
		String url = lBundle.getString("url");
		String title = lBundle.getString("title");
		String posterUrl = lBundle.getString("poster");
		final String ID = lBundle.getString("ID");
		String uploader = lBundle.getString("uploader");
		handleVoting(ID);

//		intent.putExtra(MediaPlayerActivity.SubtitleUri, subtitleUri);

//		VideoView videoView = (VideoView) findViewById(R.id.ep_video_view);
//		//Use a media controller so that you can scroll the video contents
//		//and also to pause, start the video.
//		MediaController mediaController = new MediaController(this);
//		mediaController.setAnchorView(videoView);
//		videoView.setMediaController(mediaController);
//		videoView.setVideoURI(Uri.parse(url));
//		videoView.start();
		TextView titleText = findViewById(R.id.title);
		titleText.setText(title);
		mVideoLayout = findViewById(R.id.video_layout);
		mBottomLayout = findViewById(R.id.bottom_layout);
		mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
		mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
		mVideoView.setMediaController(mMediaController);
		setVideoAreaSize();
		mVideoView.setVideoViewCallback(this);
//		mStart = (TextView) findViewById(R.id.start);
//
//		mStart.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (mSeekPosition > 0) {
//					mVideoView.seekTo(mSeekPosition);
//				}
//				mVideoView.start();
//				mMediaController.setTitle("Big Buck Bunny");
//			}
//		});


		final RecyclerView comments = findViewById(R.id.comments);
		ArrayList<Comment> lComments = new ArrayList<>();

		comments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		comments.setAdapter(new CommentAdapter(lComments));

		initializeComments(comments, lComments, ID);

		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Log.d(TAG, "onCompletion ");
			}
		});

		ImageView send = findViewById(R.id.send_comment);
		final TextView comment = findViewById(R.id.comment_content);
		final FirebaseAuth mAuth = FirebaseAuth.getInstance();
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView) {
				String text = comment.getText().toString();
				String username = mAuth.getUid();

				HashMap<String,String> hashmap = new HashMap<>();
				hashmap.put("comment", text);
				hashmap.put("UID", username);

				DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
				assert ID != null;
				ref.child("comments").child(ID).push().setValue(hashmap);

			}
		});

	}

	private void handleVoting(final String ID) {

		final boolean[] found = {false};
		final int[] upCount = {0};
		final int[] downCount = {0};
		final TextView upvotes = findViewById(R.id.upvote);
		final TextView downvotes =findViewById(R.id.down_vote);

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("votes")
				.child(ID);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {
				Iterable<DataSnapshot> data = pDataSnapshot.getChildren();

				final String UID = FirebaseAuth.getInstance().getUid();

				for(DataSnapshot d : data)
				{
					if(d.getKey().equals(UID))
					{
						found[0] = true;
					}
					if(d.child("vote").getValue().toString().equals("1"))
					{
						upCount[0]++;
					}
					else if(d.child("vote").getValue().toString().equals("-1"))
					{
						downCount[0]++;
					}

				}

				upvotes.setText(""+upCount);
				downvotes.setText(""+downCount);

				if(!found[0])
				{
					upvotes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View pView) {
							HashMap<String, String> hashmap = new HashMap<>();
							hashmap.put("vote", "1");
							DatabaseReference votes = FirebaseDatabase.getInstance().getReference().child("votes").child(ID);
							assert UID != null;
							votes.child(UID).setValue(hashmap);
						}
					});
					downvotes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View pView) {
							HashMap<String, String> hashmap = new HashMap<>();
							hashmap.put("vote", "-1");
							DatabaseReference votes = FirebaseDatabase.getInstance().getReference().child("votes").child(ID);
							assert UID != null;
							votes.child(UID).setValue(hashmap);
						}
					});
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError pDatabaseError) {

			}
		});

	}

	private void initializeComments(final RecyclerView pRecyclerView, final ArrayList<Comment> pComments1, String pID) {

		DatabaseReference lFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
		lFirebaseDatabase.child("comments").child(pID).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {
				Iterable<DataSnapshot> iterable = pDataSnapshot.getChildren();
				for(DataSnapshot d : iterable)
				{
					Comment lComment = new Comment();
					lComment.comment = (String) d.child("comment").getValue();
					lComment.comment = (String) d.child("UID").getValue();
					pComments1.add(lComment);
				}
				if(pRecyclerView.getAdapter() != null)
				{
					pRecyclerView.getAdapter().notifyDataSetChanged();
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError pDatabaseError) {

			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause ");
		if (mVideoView != null && mVideoView.isPlaying()) {
			mSeekPosition = mVideoView.getCurrentPosition();
			Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
			mVideoView.pause();
		}
	}

	/**
	 * 置视频区域大小
	 */
	private void setVideoAreaSize() {
		mVideoLayout.post(new Runnable() {
			@Override
			public void run() {
				int width = mVideoLayout.getWidth();
				cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
				ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
				videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
				videoLayoutParams.height = cachedHeight;
				mVideoLayout.setLayoutParams(videoLayoutParams);
				mVideoView.setVideoPath(VIDEO_URL);
				mVideoView.requestFocus();
			}
		});


	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
		outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
	}

	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		super.onRestoreInstanceState(outState);
		mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
		Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
	}


	@Override
	public void onScaleChange(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		if (isFullscreen) {
			ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			mVideoLayout.setLayoutParams(layoutParams);
			mBottomLayout.setVisibility(View.GONE);

		} else {
			ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = this.cachedHeight;
			mVideoLayout.setLayoutParams(layoutParams);
			mBottomLayout.setVisibility(View.VISIBLE);
		}

//		switchTitleBar(!isFullscreen);
	}

	private void switchTitleBar(boolean show) {
		android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			if (show) {
				supportActionBar.show();
			} else {
				supportActionBar.hide();
			}
		}
	}

	@Override
	public void onPause(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onPause UniversalVideoView callback");
	}

	@Override
	public void onStart(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onStart UniversalVideoView callback");
	}

	@Override
	public void onBufferingStart(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onBufferingStart UniversalVideoView callback");
	}

	@Override
	public void onBufferingEnd(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
	}

	@Override
	public void onBackPressed() {
		if (this.isFullscreen) {
			mVideoView.setFullscreen(false);
		} else {
			super.onBackPressed();
		}
	}

}
