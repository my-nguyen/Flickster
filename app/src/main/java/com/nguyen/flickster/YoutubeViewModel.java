package com.nguyen.flickster;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class YoutubeViewModel extends ViewModel {
    private MovieRepository repository;

    @Inject
    public YoutubeViewModel(MovieRepository movieRepository) {
        repository = movieRepository;
    }

    public LiveData<String> getTrailer(int movieId) {
        return repository.getTrailer(movieId);
    }
}
