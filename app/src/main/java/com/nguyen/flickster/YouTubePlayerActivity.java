package com.nguyen.flickster;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.nguyen.flickster.Defs.TMDB_NAME_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_VALUE_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_URL_PREFIX;

/**
 * Created by My on 10/12/2016.
 */

public class YouTubePlayerActivity extends YouTubeBaseActivity {
   static final String YOUTUBEPLAYER_API_KEY = "AIzaSyAaOD8M-u6mY1fL2F0D-jIHiUwNonxFKW4";

   @Override
   protected void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      setContentView(R.layout.activity_youtube);

      String id = getIntent().getStringExtra("ID_IN");
      final String TRAILERS = "trailers?";
      String url = TMDB_URL_PREFIX + id + "/" + TRAILERS + TMDB_NAME_API_KEY + TMDB_VALUE_API_KEY;
      AsyncHttpClient client = new AsyncHttpClient();
      client.get(url, new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            JSONArray youtubeTrailers = null;
            try {
               youtubeTrailers = response.getJSONArray("youtube");
               JSONObject youtubeTrailer = youtubeTrailers.getJSONObject(0);
               final String trailer = youtubeTrailer.getString("source");

               final YouTubePlayerView playerView = (YouTubePlayerView)findViewById(R.id.youtube_player);
               playerView.initialize(YOUTUBEPLAYER_API_KEY, new YouTubePlayer.OnInitializedListener() {
                  @Override
                  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                     youTubePlayer.loadVideo(trailer);
                  }

                  @Override
                  public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                  }
               });
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
         }
      });
   }

   private void fetchTrailer(String id) {

   }
}
