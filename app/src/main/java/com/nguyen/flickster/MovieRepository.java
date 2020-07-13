package com.nguyen.flickster;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MovieRepository {
    public static final String TMDB_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static final String TAG = "MoviesRepository";

    public Map<Integer, String> genres;

    private MovieAPI movieAPI;
    private int page;
    private int totalPages;

    @Inject
    public MovieRepository(MovieAPI movieAPI) {
        Log.d(TAG, "constructor");
        this.movieAPI = movieAPI;
        page = 1;
        totalPages = 1;
    }

    public LiveData<Map<Integer, String>> getGenres() {
        final MutableLiveData<Map<Integer, String>> data = new MutableLiveData<>();
        movieAPI.getGenres(TMDB_API_KEY).enqueue(new Callback<JsonGenres>() {
            @Override
            public void onResponse(Call<JsonGenres> call, Response<JsonGenres> response) {
                genres = new HashMap<>();
                for (JsonGenre genre : response.body().genres) {
                    genres.put(genre.id, genre.name);
                }
                data.setValue(genres);
            }

            @Override
            public void onFailure(Call<JsonGenres> call, Throwable t) {
                Log.d(TAG, "onFailure: failed to fetch genres");
            }
        });
        return data;
    }

    public LiveData<List<JsonMovie>> getPage() {
        final MutableLiveData<List<JsonMovie>> movies = new MutableLiveData<>();
        if (page != 1 && page >= totalPages) {
            return movies;
        }

        Call<JsonPage> call = movieAPI.getPage(TMDB_API_KEY, page);
        call.enqueue(new Callback<JsonPage>() {
            @Override
            public void onResponse(Call<JsonPage> call, Response<JsonPage> response) {
                if (page == 1) {
                    // save total pages
                    totalPages = response.body().totalPages;
                }
                movies.setValue(response.body().movies);
                page++;
            }

            @Override
            public void onFailure(Call<JsonPage> call, Throwable t) {
                Log.d(TAG, "getPage.onFailure");
            }
        });
        return movies;
    }

    public LiveData<String> getTrailer(int movieId) {
        final MutableLiveData<String> trailer = new MutableLiveData<>();
        Call<JsonTrailers> call = movieAPI.getTrailers(movieId, TMDB_API_KEY);
        call.enqueue(new Callback<JsonTrailers>() {
            @Override
            public void onResponse(Call<JsonTrailers> call, Response<JsonTrailers> response) {
                // grab the "source" field of the first youtube trailer from a list
                String source = response.body().youtube.get(0).source;
                trailer.setValue(source);
            }

            @Override
            public void onFailure(Call<JsonTrailers> call, Throwable t) {
            }
        });
        return trailer;
    }
}
