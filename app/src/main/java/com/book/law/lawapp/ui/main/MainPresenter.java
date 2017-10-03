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

package com.book.law.lawapp.ui.main;

import android.util.Log;

import com.book.law.lawapp.model.BaseHighlight;
import com.book.law.lawapp.rest.APIServices;
import com.book.law.lawapp.rest.ApiUtils;
import com.book.law.lawapp.model.UserProfile;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainPresenter{

    private static final String TAG = "MainPresenter";
    private MainMvpView view;
    private APIServices mAPIService;
    private CompositeDisposable mCompositeDisposable;
    public MainPresenter(MainMvpView view)
    {
        this.view = view;
        mCompositeDisposable = new CompositeDisposable();
        mAPIService = ApiUtils.getAPIService();
    }

    public void onDrawerOptionAboutClick() {
        view.closeNavigationDrawer();
//        view.showAboutFragment();
    }

    public void loadSearchFragment()
    {
        view.showMainActivity();
    }
    public void getUserProfileResponse(String userId){
        view.showLoading();
        mCompositeDisposable.add(mAPIService.getUserProfile(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserProfile >() {
                    @Override
                    public void onNext(UserProfile value) {
                        view.getUserProfile(value);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }

                    }
                ));
    }
    public void getUserHighlights(String userId){
        view.showLoading();
        mCompositeDisposable.add(mAPIService.getHighlights(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseHighlight>() {
                    @Override
                    public void onNext(BaseHighlight value) {
                        Log.e("Value",value.getHighlights().size()+"");
//                        view.getUserHighlights(value);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                }
                ));
    }

    public void onDrawerOptionLogoutClick() {
        view.showLoading();

//        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LogoutResponse>() {
//                    @Override
//                    public void accept(LogoutResponse response) throws Exception {
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getDataManager().setUserAsLoggedOut();
//                        getMvpView().hideLoading();
//                        getMvpView().openLoginActivity();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
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

    }


    public void onNavMenuCreated() {
//        if (!isViewAttached()) {
//            return;
//        }
//        getMvpView().updateAppVersion();
//
//        final String currentUserName = getDataManager().getCurrentUserName();
//        if (currentUserName != null && !currentUserName.isEmpty()) {
//            getMvpView().updateUserName(currentUserName);
//        }
//
//        final String currentUserEmail = getDataManager().getCurrentUserEmail();
//        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
//            getMvpView().updateUserEmail(currentUserEmail);
//        }
//
//        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
//        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
//            getMvpView().updateUserProfilePic(profilePicUrl);
//        }
    }

}
