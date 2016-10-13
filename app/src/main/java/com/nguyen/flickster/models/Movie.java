package com.nguyen.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 10/11/2016.
 */

public class Movie implements Serializable {
   public String posterPath;
   public String overview;
   public String releaseDate;
   public List<Integer> genreIds;
   public String id;
   public String originalTitle;
   public String backdropPath;
   public double popularity;
   public double voteAverage;

   public Movie(JSONObject jsonObject) throws JSONException {
      String prefix = "https://image.tmdb.org/t/p/w342";
      String poster = jsonObject.getString("poster_path");
      posterPath = prefix + poster;
      overview = jsonObject.getString("overview");
      releaseDate = jsonObject.getString("release_date");
      genreIds = new ArrayList<>();
      JSONArray array = jsonObject.getJSONArray("genre_ids");
      for (int i = 0; i < array.length(); i++)
         genreIds.add((int)array.get(i));
      id = jsonObject.getString("id");
      originalTitle = jsonObject.getString("original_title");
      String backdrop = jsonObject.getString("backdrop_path");
      backdropPath = prefix + backdrop;
      popularity = jsonObject.getDouble("popularity");
      voteAverage = jsonObject.getDouble("vote_average");
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("poster path: ").append(posterPath);
      builder.append(", backdrop path: ").append(backdropPath);
      builder.append(", original title: ").append(originalTitle);
      builder.append(", overview: ").append(overview);
      builder.append(", vote average: ").append(voteAverage);
      return builder.toString();
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
