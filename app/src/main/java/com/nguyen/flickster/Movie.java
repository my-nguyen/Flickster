package com.nguyen.flickster;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

class Movie implements Serializable {
    public static final String IMAGE_PREFIX = "https://image.tmdb.org/t/p/w342";

    @SerializedName("popularity")
    public Double popularity;
    @SerializedName("vote_count")
    public Integer voteCount;
    @SerializedName("video")
    public Boolean video;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("id")
    public Integer id;
    @SerializedName("adult")
    public Boolean adult;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("genre_ids")
    public List<Integer> genreIds = null;
    @SerializedName("title")
    public String title;
    @SerializedName("vote_average")
    public Double voteAverage;
    @SerializedName("overview")
    public String overview;
    @SerializedName("release_date")
    public String releaseDate;

    /*@Override
    public String toString() {
        return "Movie{" +
                "popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", posterPath='" + posterPath + '\'' +
                ", id=" + id +
                ", adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", genreIds=" + genreIds +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }*/
}
