package com.book.law.lawapp.ui.search;

import com.book.law.lawapp.base.BaseView;
import com.book.law.lawapp.model.Case;
import com.book.law.lawapp.utils.ErrorParser;

import java.util.List;

/**
 * Created by Diodel Gerona on 03/09/2017.
 */

public interface SearchView extends BaseView{
    void querySearchResultResponse(List<Case> cases);
    void highLightRequestResponse(ErrorParser errorParser);
}
