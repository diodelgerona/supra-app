package com.book.law.lawapp.ui.search;

import android.util.Log;

import com.book.law.lawapp.rest.APIServices;
import com.book.law.lawapp.rest.ApiUtils;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Diodel Gerona on 03/09/2017.
 */

public class SearchPresenter {
    private SearchView view;
    private APIServices mAPIService;
    private CompositeDisposable mCompositeDisposable;
    public SearchPresenter(SearchView view)
    {
        this.view =view;
        mAPIService = ApiUtils.getAPIService();
        mCompositeDisposable = new CompositeDisposable();
    }
    public void getSearchCaseResponse(String searchText)
    {
        mCompositeDisposable.add(mAPIService.getCase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Case>>()
                {
                    @Override
                    public void onNext(List<Case> value)
                    {
                        Log.d("onResponse", "post submitted to API." + value.get(0).getRef_no());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.onError("Error Occurred");
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                }));
    }
    public void onDestroy()
    {
        if(mCompositeDisposable != null)
             mCompositeDisposable.dispose();
    }

}
