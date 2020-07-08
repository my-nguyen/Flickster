package com.nguyen.flickster;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(YoutubeActivity youtubeActivity);
    void inject(MoviesAdapter moviesAdapter);

    MovieAPI getMovieAPI();
    MovieRepository getMovieRepository();
}
