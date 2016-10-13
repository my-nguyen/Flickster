package com.nguyen.flickster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nguyen.flickster.adapters.MovieArrayAdapter;
import com.nguyen.flickster.models.Genres;
import com.nguyen.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nguyen.flickster.Defs.TMDB_NAME_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_URL_PREFIX_MOVIE;
import static com.nguyen.flickster.Defs.TMDB_VALUE_API_KEY;
import static com.nguyen.flickster.Defs.TMDB_URL_PREFIX;

public class MainActivity extends AppCompatActivity {
   List<Movie> mMovies;
   MovieArrayAdapter mAdapter;
   RequestQueue mRequestQueue;
   @BindView(R.id.listView) ListView mListView;
   @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeContainer;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      ButterKnife.bind(this);

      mMovies = new ArrayList<>();
      mAdapter = new MovieArrayAdapter(this, mMovies);
      mListView.setAdapter(mAdapter);
      mRequestQueue = Volley.newRequestQueue(this);

      fetchMovies();
      fetchGenres();

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
      String url = TMDB_URL_PREFIX_MOVIE + NOW_PLAYING + TMDB_NAME_API_KEY + TMDB_VALUE_API_KEY;
      JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                  JSONArray jsonArray = null;
                  try {
                     jsonArray = response.getJSONArray("results");
                     // clear out old items before appending new ones
                     mAdapter.clear();
                     // add new items to the adapter
                     mAdapter.addAll(Movie.fromJSONArray(jsonArray));
                     Log.d("TRUONG", mMovies.toString());
                     // signal that refresh has finished
                     mSwipeContainer.setRefreshing(false);
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
      // Add your Requests to the RequestQueue to execute
      mRequestQueue.add(req);
   }

   void fetchGenres() {
      final String TMDB_TABLE_GENRE = "genre/movie/";
      final String LIST = "list?";
      String url = TMDB_URL_PREFIX + TMDB_TABLE_GENRE + LIST + TMDB_NAME_API_KEY + TMDB_VALUE_API_KEY;
      JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                  JSONArray jsonArray = null;
                  try {
                     jsonArray = response.getJSONArray("genres");
                     mAdapter.sAllGenres = new Genres(jsonArray);
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
      // Add your Requests to the RequestQueue to execute
      mRequestQueue.add(req);
   }
}
