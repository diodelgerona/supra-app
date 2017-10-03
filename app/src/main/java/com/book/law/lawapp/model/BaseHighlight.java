package com.book.law.lawapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Diodel Gerona on 27/09/2017.
 */

public class BaseHighlight {
    @SerializedName("highlights")
    @Expose
    private List<Highlights> highlights;

    public List<Highlights> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlights> highlights) {
        this.highlights = highlights;
    }

}
