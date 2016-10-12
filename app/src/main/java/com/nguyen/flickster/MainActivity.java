package com.nguyen.flickster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nguyen.flickster.adapters.MovieArrayAdapter;
import com.nguyen.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.nguyen.flickster.Defs.TMDB_NAME_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_VALUE_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_URL_PREFIX;

public class MainActivity extends AppCompatActivity {
   List<Movie> mMovies;
   MovieArrayAdapter mAdapter;
   ListView mListView;
   SwipeRefreshLayout mSwipeContainer;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mListView = (ListView)findViewById(R.id.listView);
      mMovies = new ArrayList<>();
      int orientation = getResources().getConfiguration().orientation;
      mAdapter = new MovieArrayAdapter(this, mMovies, orientation);
      mListView.setAdapter(mAdapter);

      fetchMovies();

      mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
      mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         @Override
         public void onRefresh() {
            fetchMovies();
         }
      });
      // Configure the refreshing colors
      mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

      mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Movie movie = mMovies.get(i);
            if (movie.voteAverage >= 5.0) {
               Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);
               intent.putExtra("ID_IN", movie.id);
               startActivity(intent);
            } else {
               Intent intent = new Intent(MainActivity.this, DetailActivity.class);
               intent.putExtra("MOVIE_IN", movie);
               startActivity(intent);
            }
         }
      });
   }

   private void fetchMovies() {
      final String NOW_PLAYING = "now_playing?";
      String url = TMDB_URL_PREFIX + NOW_PLAYING + TMDB_NAME_API_KEY + TMDB_VALUE_API_KEY;
      AsyncHttpClient client = new AsyncHttpClient();
      client.get(url, new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            JSONArray movieJsonResults = null;
            try {
               movieJsonResults = response.getJSONArray("results");
               // clear out old items before appending new ones
               mAdapter.clear();
               // add new items to the adapter
               mAdapter.addAll(Movie.fromJSONArray(movieJsonResults));
               Log.d("TRUONG", mMovies.toString());
               // signal that refresh has finished
               mSwipeContainer.setRefreshing(false);
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
}
