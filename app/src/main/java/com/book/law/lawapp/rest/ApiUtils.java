package com.book.law.lawapp.rest;

/**
 * Created by Diodel Gerona on 02/09/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://www.mocky.io/";

    public static APIServices getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIServices.class);
    }
}