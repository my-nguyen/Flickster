package com.nguyen.flickster;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Trailers {
    @SerializedName("id")
    public Integer id;
    @SerializedName("quicktime")
    public List<Object> quicktime = null;
    @SerializedName("youtube")
    public List<Youtube> youtube = null;
}
