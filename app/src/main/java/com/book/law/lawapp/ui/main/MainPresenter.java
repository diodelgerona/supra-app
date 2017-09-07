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

import java.util.List;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainPresenter{

    private static final String TAG = "MainPresenter";
    private MainMvpView view;
    public MainPresenter(MainMvpView view)
    {
        this.view = view;
    }

    public void onDrawerOptionAboutClick() {
        view.closeNavigationDrawer();
//        view.showAboutFragment();
    }

    public void loadSearchFragment()
    {
        view.showMainActivity();
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
