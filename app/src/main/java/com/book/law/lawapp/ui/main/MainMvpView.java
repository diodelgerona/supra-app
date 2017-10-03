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

import com.book.law.lawapp.base.BaseView;
import com.book.law.lawapp.model.BaseHighlight;
import com.book.law.lawapp.model.UserProfile;

/**
 * Created by janisharali on 27/01/17.
 */

public interface MainMvpView extends BaseView {

    void openLoginActivity();

    void showMainActivity();

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String currentUserProfilePicUrl);

    void updateAppVersion();
    void getUserProfile(UserProfile userProfile);
    void getUserHighlights(BaseHighlight highlights);
    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();
}
