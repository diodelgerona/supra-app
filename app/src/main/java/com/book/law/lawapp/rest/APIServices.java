package com.book.law.lawapp.rest;

/**
 * Created by Diodel Gerona on 02/09/2017.
 */

import com.book.law.lawapp.model.User;
import com.book.law.lawapp.model.UserProfile;
import com.book.law.lawapp.model.BaseHighlight;
import com.book.law.lawapp.model.Case;
import com.book.law.lawapp.utils.ErrorParser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {
    @FormUrlEncoded
    @POST("/v1/highlight-case/{case_id}")
    Observable<ErrorParser> storeHightlitedTextToRemote(@Path("case_id") String caseId, @Field("user_id") String userId, @Field("text") String text);

    @FormUrlEncoded
    @POST("/v1/search-case")
    Observable<List<Case>> getSearchedCasesFromRemote(@Field("search") String searchedText);

    @FormUrlEncoded
    @POST("/v1/auth/login")
    Observable<User> getLoginCredentialsFromRemote(@Field("type") String type,@Field("token") String token,@Field("email") String email,@Field("password") String password);

    @GET("/v1/user/view/{user_id}")
    Observable<UserProfile> getUserProfile(@Path("user_id") String userId);

    @GET("/v1/highlights/{user_id}")
    Observable<BaseHighlight> getHighlights(@Path("user_id") String userId);

}