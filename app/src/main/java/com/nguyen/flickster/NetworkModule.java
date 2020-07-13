package com.nguyen.flickster;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";

    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    Retrofit provideRetrofit(GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(factory.create())
                .build();
    }

    @Provides
    MovieAPI provideMovieAPI(Retrofit retrofit) {
        return retrofit.create(MovieAPI.class);
    }
}
