package com.book.law.lawapp.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diodel Gerona on 10/09/2017.
 */

public class ErrorParser {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("search")
    @Expose
    private List<String> search = new ArrayList<>();
    private String error;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public List<String> getSearch() {
        return search;
    }

    public void setSearch(List<String> search) {
        this.search = search;
    }
}
