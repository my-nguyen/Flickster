package com.nguyen.flickster;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    @SerializedName("results")
    public List<Movie> movies = null;
    @SerializedName("page")
    public Integer page;
    @SerializedName("total_results")
    public Integer totalResults;
    @SerializedName("total_pages")
    public Integer totalPages;
}
