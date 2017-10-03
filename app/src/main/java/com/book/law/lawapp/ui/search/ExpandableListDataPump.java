package com.book.law.lawapp.ui.search;


import android.text.TextUtils;
import android.util.Log;

import com.book.law.lawapp.model.Case;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData(Case caseL) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> title = new ArrayList<String>();
        title.add(caseL.getTitle());
        if (!TextUtils.isEmpty(caseL.getRef_no()))
            title.add(caseL.getRef_no());
        if (!TextUtils.isEmpty(caseL.getTopic()))
             title.add(caseL.getTopic());
        if (!TextUtils.isEmpty(caseL.getGrno()))
            title.add(caseL.getGrno());

        List<String> date = new ArrayList<String>();
        date.add("January 1, 2017 - December 25, 2017");


        List<String> Topic = new ArrayList<String>();
        Topic.add(caseL.getTopic());

        List<String> Syllables = new ArrayList<String>();
        Syllables.add(caseL.getSyllables());
        Log.e("syllables ",caseL.getSyllables());
        List<String> Body = new ArrayList<String>();
        Body.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius scelerisque ligula a mollis. Aliquam pellentesque viverra ultricies. Vivamus pulvinar est eget velit tempor porta. Aenean placerat accumsan volutpat. Praesent laoreet euismod dui vitae tincidunt. Suspendisse tincidunt hendrerit orci, at luctus lacus ornare vitae. Sed quis volutpat elit, quis pellentesque velit. Pellentesque tempus cursus ex at euismod. Suspendisse potenti. Praesent scelerisque mattis facilisis. Duis iaculis a neque eu semper. Etiam nec tempus erat. Aenean cursus posuere ex, in molestie ex commodo ut. Quisque tempus est leo, non elementum leo tristique at. Morbi ac dolor libero.");


        List<String> Full = new ArrayList<String>();
        Full.add("http://www.lipsum.com/feed/html");

        expandableListDetail.put("Title", title);
        expandableListDetail.put("Date", date);
        expandableListDetail.put("Topic", Topic);
        expandableListDetail.put("Syllables", Syllables);
        expandableListDetail.put("Body", Body);
        expandableListDetail.put("Full text", Full);

        return expandableListDetail;
    }
    public static LinkedHashMap<String, List<String>> getDataCase2() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Rei Mark vs. Diodel Gerona");
        cricket.add("Defendant  - Rei Mark");
        cricket.add("Compliant - Diodel Gerona");
        cricket.add("Not Controlling");
        List<String> football = new ArrayList<String>();
        football.add("G.R No. 11111");
        football.add("251SCRA 12345");

        List<String> date = new ArrayList<String>();
        date.add("February 20, 2016 - January 3, 2017");


        List<String> Topic = new ArrayList<String>();
        Topic.add("Murder");

        List<String> Syllables = new ArrayList<String>();
        Syllables.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius scelerisque ligula a mollis.");

        List<String> Body = new ArrayList<String>();
        Body.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius scelerisque ligula a mollis. Aliquam pellentesque viverra ultricies. Vivamus pulvinar est eget velit tempor porta. Aenean placerat accumsan volutpat. Praesent laoreet euismod dui vitae tincidunt. Suspendisse tincidunt hendrerit orci, at luctus lacus ornare vitae. Sed quis volutpat elit, quis pellentesque velit. Pellentesque tempus cursus ex at euismod. Suspendisse potenti. Praesent scelerisque mattis facilisis. Duis iaculis a neque eu semper. Etiam nec tempus erat. Aenean cursus posuere ex, in molestie ex commodo ut. Quisque tempus est leo, non elementum leo tristique at. Morbi ac dolor libero.");


        List<String> Full = new ArrayList<String>();
        Full.add("http://www.lipsum.com/feed/html");

        expandableListDetail.put("Title", cricket);
        expandableListDetail.put("Reference No.", football);
        expandableListDetail.put("Date", date);
        expandableListDetail.put("Topic", Topic);
        expandableListDetail.put("Syllables", Syllables);
        expandableListDetail.put("Body", Body);
        expandableListDetail.put("Full text", Full);

        return expandableListDetail;
    }
}
