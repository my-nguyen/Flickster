package com.nguyen.flickster;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.nguyen.flickster.databinding.ActivityYoutubeBinding;

import javax.inject.Inject;

/**
 * Created by My on 10/12/2016.
 */
public class YoutubeActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private static final String TAG = "YoutubeActivity";
    private static final String YOUTUBE_API_KEY = "AIzaSyAaOD8M-u6mY1fL2F0D-jIHiUwNonxFKW4";

    @Inject
    YoutubeViewModel youtubeViewModel;

    private ActivityYoutubeBinding binding;

    @Override
    protected void onCreate(Bundle bundle) {
        ((MyApplication)getApplicationContext()).appComponent.inject(this);

        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube);

        // youtubeViewModel = new ViewModelProvider(this).get(YoutubeViewModel.class);
        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        youtubeViewModel.getTrailer(movieId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String source) {
                YouTubePlayerSupportFragmentX youtube = (YouTubePlayerSupportFragmentX)getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
                youtube.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(source);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d(TAG, "onCreate.enqueue.onResponse.onInitializationFailure");
                    }
                });
            }
        });
    }
}
