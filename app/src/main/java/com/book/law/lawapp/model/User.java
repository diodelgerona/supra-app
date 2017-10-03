package com.book.law.lawapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Diodel Gerona on 16/09/2017.
 */

public class User {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("expired_date")
    @Expose
    private String expired_date;
    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;
    @SerializedName("refresh_token_expired_date")
    @Expose
    private String refresh_token_expired_date;
    @SerializedName("msg")
    @Expose
    private String errorMsg;
    @SerializedName("user_profile")
    @Expose
    private UserProfile userProfile;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token_expired_date() {
        return refresh_token_expired_date;
    }

    public void setRefresh_token_expired_date(String refresh_token_expired_date) {
        this.refresh_token_expired_date = refresh_token_expired_date;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
