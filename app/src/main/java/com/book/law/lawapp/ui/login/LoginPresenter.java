/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.book.law.lawapp.ui.login;


import android.util.Log;

import com.book.law.lawapp.R;
import com.book.law.lawapp.model.User;
import com.book.law.lawapp.rest.APIServices;
import com.book.law.lawapp.rest.ApiUtils;
import com.book.law.lawapp.utils.CommonUtils;
import com.book.law.lawapp.utils.ErrorParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by janisharali on 27/01/17.
 */

public class LoginPresenter {

    private static final String TAG = "LoginPresenter";
    private LoginView view;
    private APIServices mAPIService;
    private CompositeDisposable mCompositeDisposable;
    public LoginPresenter(LoginView view){
        this.view = view;
        mAPIService = ApiUtils.getAPIService();
        mCompositeDisposable = new CompositeDisposable();
    }
    public void onGoogleLoginClick() {
//        view.openMainActivity();
    }
    public void normalLoginClick(String type,String token,String email,String password){
        if (email == null || email.isEmpty()) {
            view.onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            view.onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            view.onError(R.string.empty_password);
            return;
        }

        view.showLoading();
        mCompositeDisposable.add(mAPIService.getLoginCredentialsFromRemote(type, token, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User value) {
                        if(value.getErrorMsg() != null)
                            view.onError(value.getErrorMsg());
                        else
                            view.openMainActivity(value);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            TypeAdapter<ErrorParser> adapter = gson.getAdapter
                                    (ErrorParser
                                            .class);
                            try {
                                ErrorParser errorParser =
                                        adapter.fromJson(body.string());
                                Log.e("errorParser",errorParser.getMessage());
                                String errorMsg =((errorParser == null) || (errorParser.getMessage() == null) ?errorParser.getMsg() : (errorParser.getMessage()));
                                view.onError(errorMsg);
                                view.hideLoading();

                            } catch (IOException ee) {
                                Log.e("IOException",ee.getMessage());
                                ee.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
    public void onDestroy()
    {
        if(mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }
//

//    public LoginPresenter(DataManager dataManager,
//                          SchedulerProvider schedulerProvider,
//                          CompositeDisposable compositeDisposable) {
//        super(dataManager, schedulerProvider, compositeDisposable);
//    }
//
//    public void onServerLoginClick(String email, String password) {
//        //validate email and password
//        if (email == null || email.isEmpty()) {
//            getMvpView().onError(R.string.empty_email);
//            return;
//        }
//        if (!CommonUtils.isEmailValid(email)) {
//            getMvpView().onError(R.string.invalid_email);
//            return;
//        }
//        if (password == null || password.isEmpty()) {
//            getMvpView().onError(R.string.empty_password);
//            return;
//        }
//        getMvpView().showLoading();
//
//        getCompositeDisposable().add(getDataManager()
//                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LoginResponse>() {
//                    @Override
//                    public void accept(LoginResponse response) throws Exception {
//                        getDataManager().updateUserInfo(
//                                response.getAccessToken(),
//                                response.getUserId(),
//                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
//                                response.getUserName(),
//                                response.getUserEmail(),
//                                response.getGoogleProfilePicUrl());
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//                        getMvpView().openMainActivity();
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//
//                        // handle the login error here
//                        if (throwable instanceof ANError) {
//                            ANError anError = (ANError) throwable;
//                            handleApiError(anError);
//                        }
//                    }
//                }));
//    }
//
//    public void onGoogleLoginClick() {
//        // instruct LoginActivity to initiate google login
//        getMvpView().showLoading();
//
//        getCompositeDisposable().add(getDataManager()
//                .doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest("test1", "test1"))
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LoginResponse>() {
//                    @Override
//                    public void accept(LoginResponse response) throws Exception {
//                        getDataManager().updateUserInfo(
//                                response.getAccessToken(),
//                                response.getUserId(),
//                                DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE,
//                                response.getUserName(),
//                                response.getUserEmail(),
//                                response.getGoogleProfilePicUrl());
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//                        getMvpView().openMainActivity();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//
//                        // handle the login error here
//                        if (throwable instanceof ANError) {
//                            ANError anError = (ANError) throwable;
//                            handleApiError(anError);
//                        }
//                    }
//                }));
//    }
//
//    @Override
//    public void onFacebookLoginClick() {
//        // instruct LoginActivity to initiate facebook login
//        getMvpView().showLoading();
//
//        getCompositeDisposable().add(getDataManager()
//                .doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest("test3", "test4"))
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LoginResponse>() {
//                    @Override
//                    public void accept(LoginResponse response) throws Exception {
//                        getDataManager().updateUserInfo(
//                                response.getAccessToken(),
//                                response.getUserId(),
//                                DataManager.LoggedInMode.LOGGED_IN_MODE_FB,
//                                response.getUserName(),
//                                response.getUserEmail(),
//                                response.getGoogleProfilePicUrl());
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//                        getMvpView().openMainActivity();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//
//                        // handle the login error here
//                        if (throwable instanceof ANError) {
//                            ANError anError = (ANError) throwable;
//                            handleApiError(anError);
//                        }
//                    }
//                }));
//    }
}
