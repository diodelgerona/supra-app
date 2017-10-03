package com.book.law.lawapp.ui.search;

import android.util.Log;

import com.book.law.lawapp.model.Case;
import com.book.law.lawapp.rest.APIServices;
import com.book.law.lawapp.rest.ApiUtils;
import com.book.law.lawapp.utils.ErrorParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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
    public void getSearchCase(String searchText)
    {
        mCompositeDisposable.add(mAPIService.getSearchedCasesFromRemote(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Case>>()
                {
                    @Override
                    public void onNext(List<Case> value)
                    {
                        view.querySearchResultResponse(value);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            TypeAdapter<ErrorParser> adapter = gson.getAdapter
                                    (ErrorParser
                                            .class);
                            try {
                                ErrorParser errorParser =
                                        adapter.fromJson(body.string());
                                Log.e("errorParser",errorParser.getSearch().get(0));
                                view.onError(errorParser.getSearch().get(0));

                            } catch (IOException ee) {
                                Log.e("IOException",ee.getMessage());
                                ee.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onComplete()
                    {

                    }
                }));
    }

    public void storeHighlightedTextRequest(String caseId, String userId ,String text){
        mCompositeDisposable.add(mAPIService.storeHightlitedTextToRemote(caseId,userId,text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ErrorParser>() {
                    @Override
                    public void onNext(ErrorParser value) {

                    }

                    @Override
                    public void onError(Throwable e) {

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

}
