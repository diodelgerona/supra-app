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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.book.law.lawapp.R;
import com.book.law.lawapp.base.BaseActivity;
import com.book.law.lawapp.model.User;
import com.book.law.lawapp.ui.main.MainActivity;
import com.book.law.lawapp.utils.AppConstants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by janisharali on 27/01/17.
 */

public class LoginActivity extends BaseActivity implements LoginView,GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 007;
    private SharedPreferences sharedpreferences;
    @BindView(R.id.et_email)
    EditText mEmailEditText;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;
    private  LoginPresenter presenter;
    private  GoogleApiClient mGoogleApiClient;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUnBinder(ButterKnife.bind(this));
        presenter = new LoginPresenter(this);
        sharedpreferences = getSharedPreferences(AppConstants.PREF_NAME,
                Context.MODE_PRIVATE);
        String userId = sharedpreferences.getString(AppConstants.LOGIN_USER_ID,"");
        if(TextUtils.isEmpty(userId)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/
//        mPresenter.onAttach(LoginActivity.this);
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        Log.e("requestCode",requestCode+""+data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
 /*       OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email + ", Image: " + personPhotoUrl);
            presenter.onGoogleLoginClick();
//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);
//
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }
    @OnClick(R.id.btn_server_login)
    void onServerLoginClick(View v) {
        presenter.normalLoginClick(AppConstants.LOGIN_TYPE_NORMAL,"",mEmailEditText.getText().toString(),mPasswordEditText.getText().toString());
//        mPresenter.onServerLoginClick(mEmailEditText.getText().toString(),
//                mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.ib_google_login)
    void onGoogleLoginClick(View v) {
//        signIn();
    }


    @Override
    public void onError(String message) {
        super.onError(message);
    }

    @Override
    public void showLoading() {
        super.showLoading();

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void openMainActivity(User user){
        if(user!= null){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(AppConstants.LOGIN_USER_ID, user.getUserProfile().getUser_id());
            editor.putString(AppConstants.LOGIN_TOKEN_EXPIRE_DATE, user.getExpired_date());
            editor.putString(AppConstants.LOGIN_ACCESS_TOKEN, user.getAccess_token());
            editor.putString(AppConstants.LOGIN_REFRESH_TOKEN, user.getRefresh_token());
            editor.putString(AppConstants.LOGIN_PREMIUM, user.getUserProfile().getPremium());
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEmailEditText.setText("");
        mPasswordEditText.setText("");
    }

    @Override
    protected void onDestroy() {
//        mPresenter.onDetach();
          super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
