package com.book.law.lawapp.rest;

/**
 * Created by Diodel Gerona on 02/09/2017.
 */

import com.book.law.lawapp.model.SampleModel;
import com.book.law.lawapp.ui.search.Case;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET("/v2/59ad786e2d0000a80a9b7e2f")
    Observable<List<Case>> getCase();

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SampleModel> getAnswers(@Query("tagged") String tags);
    @GET("/rest/ios/products/get_categories")
    Observable<List<ProductCategory>> getCategories();
}