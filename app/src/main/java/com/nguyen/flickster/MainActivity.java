package com.nguyen.flickster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
   List<Movie> movies;
   MovieArrayAdapter adapter;
   ListView listView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      listView = (ListView)findViewById(R.id.listView);
      movies = new ArrayList<>();
      adapter = new MovieArrayAdapter(this, movies);
      listView.setAdapter(adapter);
      String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
      AsyncHttpClient client = new AsyncHttpClient();
      client.get(url, new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            JSONArray movieJsonResults = null;
            try {
               movieJsonResults = response.getJSONArray("results");
               movies.addAll(Movie.fromJSONArray(movieJsonResults));
               adapter.notifyDataSetChanged();
               Log.d("NGUYEN", movies.toString());
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
