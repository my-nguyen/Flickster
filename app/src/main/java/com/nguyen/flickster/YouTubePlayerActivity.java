package com.nguyen.flickster;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nguyen.flickster.Defs.TMDB_NAME_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_VALUE_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_URL_PREFIX;

/**
 * Created by My on 10/12/2016.
 */

public class YouTubePlayerActivity extends YouTubeBaseActivity {
   static final String YOUTUBEPLAYER_API_KEY = "AIzaSyAaOD8M-u6mY1fL2F0D-jIHiUwNonxFKW4";
   RequestQueue mRequestQueue;
   @BindView(R.id.youtube_player) YouTubePlayerView playerView;

   @Override
   protected void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      setContentView(R.layout.activity_youtube);

      ButterKnife.bind(this);

      mRequestQueue = Volley.newRequestQueue(this);
      String id = getIntent().getStringExtra("ID_IN");
      final String TRAILERS = "trailers?";
      String url = TMDB_URL_PREFIX + id + "/" + TRAILERS + TMDB_NAME_API_KEY + TMDB_VALUE_API_KEY;
      JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                  JSONArray youtubeTrailers = null;
                  try {
                     youtubeTrailers = response.getJSONArray("youtube");
                     JSONObject youtubeTrailer = youtubeTrailers.getJSONObject(0);
                     final String trailer = youtubeTrailer.getString("source");

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
            },
            new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                  VolleyLog.e("Error: ", error.getMessage());
               }
            }
      );
      mRequestQueue.add(req);
   }
}
