package com.book.law.lawapp.base;

import android.support.annotation.StringRes;

/**
 * Created by Diodel Gerona on 03/09/2017.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
