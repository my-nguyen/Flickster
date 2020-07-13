package com.nguyen.flickster;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class JsonGenres {
    @SerializedName("genres")
    public List<JsonGenre> genres = null;
}
