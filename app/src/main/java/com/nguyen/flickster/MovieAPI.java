package com.nguyen.flickster;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface MovieAPI {
    // https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
    @GET("genre/movie/list")
    Call<JsonGenres> getGenres(@Query("api_key") String apiKey);

    // https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&page=1
    @GET("movie/now_playing")
    Call<JsonPage> getPage(@Query("api_key") String apiKey, @Query("page") int page);

    // https://api.themoviedb.org/3/movie/606679/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
    @GET("movie/{id}/trailers")
    Call<JsonTrailers> getTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);
}
