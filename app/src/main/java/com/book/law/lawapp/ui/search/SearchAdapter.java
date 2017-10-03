package com.book.law.lawapp.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.book.law.lawapp.R;
import com.book.law.lawapp.model.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diodel Gerona on 09/09/2017.
 */

public class SearchAdapter extends BaseAdapter implements Filterable{


    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Case> resultList = new ArrayList<>();

    public SearchAdapter(Context context) {
        mContext = context;
    }
    public void setResultList(List<Case> resultList){
        this.resultList.clear();
        this.resultList = resultList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Case getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_result_item, parent, false);
        }
        Log.e("getItem",getItem(position).getId()+"");
        if(getItem(position).getId() == -0){
            ((TextView) convertView.findViewById(R.id.tvSearchResult)).setText(mContext.getString(R.string.searched_no_match));
        }
        else {
            ((TextView) convertView.findViewById(R.id.tvSearchResult)).setText(getItem(position).getTitle()+"");
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Case> books = resultList;

                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Case>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
//    private List<Case> findBooks(Context context, String bookTitle) {
//        // GoogleBooksProtocol is a wrapper for the Google Books API
//        GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
//        return protocol.findBooks(bookTitle);
//    }
}
