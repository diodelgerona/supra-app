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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.book.law.lawapp.BuildConfig;
import com.book.law.lawapp.R;
import com.book.law.lawapp.base.BaseActivity;
import com.book.law.lawapp.base.FragmentInteractionListiner;
import com.book.law.lawapp.model.BaseHighlight;
import com.book.law.lawapp.ui.login.LoginActivity;
import com.book.law.lawapp.model.UserProfile;
import com.book.law.lawapp.ui.search.SearchFragment;
import com.book.law.lawapp.utils.AppConstants;
import com.book.law.lawapp.utils.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainActivity extends BaseActivity implements MainMvpView ,FragmentInteractionListiner{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private TextView tvEmail,tvFirstName,tvLastName,tvAddress,tvLogout;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.tv_app_version)
    TextView mAppVersionTextView;

    private TextView mNameTextView;
    private MainPresenter presenter;
    private TextView mEmailTextView;
    List<String> title = new ArrayList<>();
    private RoundedImageView mProfileImageView;

    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences sharedpreferences;
    private  String userId;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUnBinder(ButterKnife.bind(this));
        presenter = new MainPresenter(this);
        sharedpreferences = getSharedPreferences(AppConstants.PREF_NAME,
                Context.MODE_PRIVATE);
        userId = sharedpreferences.getString(AppConstants.LOGIN_USER_ID,"");
        setUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentByTag(SearchFragment.TAG);
//        if (fragment == null) {
//
//        } else {
//            onFragmentDetached(SearchFragment.TAG);
//        }
    }
    @Override
    public void updateAppVersion() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mAppVersionTextView.setText(version);
    }

    @Override
    public void getUserProfile(UserProfile userProfile) {
        tvEmail.setText(((userProfile.getEmail() == null) ?  "No Email" : userProfile.getEmail()));
        tvFirstName.setText(((userProfile.getFirst_name() == null) ?"No FirstName" : userProfile.getFirst_name()));
        tvLastName.setText(((userProfile.getLast_name() == null) ?"No LastName" : userProfile.getLast_name()));
        tvAddress.setText(((userProfile.getAddress() == null) ?"No Address" : userProfile.getAddress()));
    }

    @Override
    public void getUserHighlights(BaseHighlight highlights) {
        Log.e("Highlights",highlights.getHighlights().size()+"");
    }

    @Override
    public void updateUserName(String currentUserName) {
        mNameTextView.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mEmailTextView.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {
        //load profile pic url into ANImageView
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
//        mPresenter.onDetach();
        super.onDestroy();
    }

//    @Override
//    public void onFragmentAttached() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .add(R.id.cl_root_view, SearchFragment.newInstance(), SearchFragment.TAG)
//                .commit();
//    }

    @Override
    public void onFragmentAttached() {
        super.onFragmentAttached();
    }

    @Override
    public void onFragmentDetached(String tag) {
        super.onFragmentDetached(tag);
    }

    @Override
    public void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        presenter.loadSearchFragment();
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
//        mPresenter.onNavMenuCreated();
//        mPresenter.onViewInitialized();
    }

//    private void setupCardContainerView() {
//
//        int screenWidth = ScreenUtils.getScreenWidth(this);
//        int screenHeight = ScreenUtils.getScreenHeight(this);
//
//        mCardsContainerView.getBuilder()
//                .setDisplayViewCount(3)
//                .setHeightSwipeDistFactor(10)
//                .setWidthSwipeDistFactor(5)
//                .setSwipeDecor(new SwipeDecor()
//                        .setViewWidth((int) (0.90 * screenWidth))
//                        .setViewHeight((int) (0.75 * screenHeight))
//                        .setPaddingTop(20)
//                        .setSwipeRotationAngle(10)
//                        .setRelativeScale(0.01f));
//
//        mCardsContainerView.addItemRemoveListener(new ItemRemovedListener() {
//            @Override
//            public void onItemRemoved(int count) {
//                if (count == 0) {
//                    // reload the contents again after 1 sec delay
//                    new Handler(getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPresenter.onCardExhausted();
//                        }
//                    }, 800);
//                }
//            }
//        });
//    }

    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);
        mProfileImageView = (RoundedImageView) headerLayout.findViewById(R.id.iv_profile_pic);
        tvFirstName = (TextView) headerLayout.findViewById(R.id.tv_first_name);
        tvLastName = (TextView) headerLayout.findViewById(R.id.tv_last_name);
        tvEmail = (TextView) headerLayout.findViewById(R.id.tv_mail);
        tvAddress = (TextView) headerLayout.findViewById(R.id.tv_address);
        tvLogout = (TextView) headerLayout.findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                clearStoredPref();
                new Handler().postDelayed(new Runnable() {@Override public void run() {
                    hideLoading();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }}, 300);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_item_logout:
//                                showLoading();
//                                clearStoredPref();
//                                new Handler().postDelayed(new Runnable() {@Override public void run() {
//                                    hideLoading();
//                                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                                    finish();
//                                }}, 300);
                            presenter.getUserHighlights("1");
//                                mPresenter.onDrawerOptionLogoutClick();
                                
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        presenter.getUserProfileResponse(userId);
    }
    public void clearStoredPref()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    public void openLoginActivity() {
//        startActivity(LoginActivity.getStartIntent(this));
//        finish();
    }

    @Override
    public void showMainActivity() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, SearchFragment.newInstance("",""),"SearchFragment" )
                .commit();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.cl_root_view, BlankFragment.newInstance("",""),"BlankFragment" );
//        transaction.commit(); // co
    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void passData(String data) {

    }
}
