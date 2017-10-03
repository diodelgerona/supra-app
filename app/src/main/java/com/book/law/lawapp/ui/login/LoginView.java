package com.book.law.lawapp.ui.login;

import com.book.law.lawapp.base.BaseView;
import com.book.law.lawapp.model.User;

/**
 * Created by Diodel Gerona on 16/09/2017.
 */

public interface LoginView extends BaseView {
    void openMainActivity(User user);
}
