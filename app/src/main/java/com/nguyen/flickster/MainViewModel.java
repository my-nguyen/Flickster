package com.nguyen.flickster;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    private MovieRepository repository;

    @Inject
    public MainViewModel(MovieRepository movieRepository) {
        Log.d(TAG, "constructor");
        repository = movieRepository;
    }

    public LiveData<Map<Integer, String>> getGenres() {
        return repository.getGenres();
    }

    public LiveData<List<JsonMovie>> getMovies() {
        return repository.getPage();
    }
}
