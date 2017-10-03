package com.book.law.lawapp.rest;

/**
 * Created by Diodel Gerona on 02/09/2017.
 */

import android.util.Log;

import com.book.law.lawapp.BuildConfig;
import com.book.law.lawapp.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String TAG = "RetrofitClient";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(50, TimeUnit.SECONDS);
            builder.connectTimeout(50, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            builder.addInterceptor(new Interceptor() {
                @Override public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+"value").build();
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter(AppConstants.CLIENT_NAME,AppConstants.CLIENT_NAME_VALUE)
                            .addQueryParameter(AppConstants.CLIENT_SECRET,AppConstants.CLIENT_SECRET_VALUE)
                            .build();
                    request = request.newBuilder().url(url)
                            .header(AppConstants.AUTHORIZATION,"Bearer cv3ocIcEqQDyviMFdJq4AmKtaoUZoscFbIEIGjRsCxkGWTehAgz6Mwfzv3BuQb6YoT6ybwpK5bf")
                            .build();
                    return chain.proceed(request);
                }
            });
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();

        }
        return retrofit;
    }
}
