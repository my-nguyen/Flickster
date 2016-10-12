package com.nguyen.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 10/11/2016.
 */

public class Movie {
   public String posterPath;
   public String backdropPath;
   public String originalTitle;
   public String overview;

   public Movie(JSONObject jsonObject) throws JSONException {
      String prefix = "https://image.tmdb.org/t/p/w342";
      String poster = jsonObject.getString("poster_path");
      posterPath = prefix + poster;
      String backdrop = jsonObject.getString("backdrop_path");
      backdropPath = prefix + backdrop;
      originalTitle = jsonObject.getString("original_title");
      overview = jsonObject.getString("overview");
   }

   public static List<Movie> fromJSONArray(JSONArray array) {
      List<Movie> movies = new ArrayList<>();
      for (int i = 0; i < array.length(); i++) {
         try {
            JSONObject object = array.getJSONObject(i);
            Movie movie = new Movie(object);
            movies.add(movie);
         } catch (JSONException e) {
            e.printStackTrace();
         }
      }
      return movies;
   }
}
