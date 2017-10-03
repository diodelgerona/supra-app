package com.book.law.lawapp.ui.search;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.book.law.lawapp.R;
import com.book.law.lawapp.base.BaseFragment;
import com.book.law.lawapp.base.FragmentInteractionListiner;
import com.book.law.lawapp.model.Case;
import com.book.law.lawapp.utils.AppConstants;
import com.book.law.lawapp.utils.CommonUtils;
import com.book.law.lawapp.utils.ErrorParser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment implements com.book.law.lawapp.ui.search.SearchView,SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "SearchFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String userId;
    private String mParam2;
    @BindView(R.id.lvResult)
    ListView lvResult;

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
//    @BindView(R.id.searchView)
    SearchView editsearch;
    private FragmentInteractionListiner mListener;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private List<String> title = new ArrayList<>();
    private SearchPresenter presenter;
    private SearchAdapter adapter;
    private View view;
    private SharedPreferences sharedpreferences;
    private static DownloadManager downloadManager;
    private String caseId ="",page ="01";
    private android.content.ClipboardManager clipboard;
    private android.content.ClipData clip;
    private static long downloadReference;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_search, container, false);
        editsearch = (SearchView) view.findViewById(R.id.searchView);
        editsearch.setOnQueryTextListener(this);
        presenter =new SearchPresenter(this);
        adapter = new SearchAdapter(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(AppConstants.PREF_NAME,
                Context.MODE_PRIVATE);
        userId = sharedpreferences.getString(AppConstants.LOGIN_USER_ID, "");
        clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(downloadReceiver, filter);

        clearClipBoard();
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                copyHighLightedText();
            }
        });
        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadReference == referenceId){

                int ch;
                ParcelFileDescriptor file;
                StringBuffer strContent = new StringBuffer("");
                StringBuffer countryData = new StringBuffer("");

                //parse the JSON data and display on the screen
                try {
                    file = downloadManager.openDownloadedFile(downloadReference);
                    FileInputStream fileInputStream
                            = new ParcelFileDescriptor.AutoCloseInputStream(file);

                    while( (ch = fileInputStream.read()) != -1)
                        strContent.append((char)ch);

                    JSONObject responseObj = new JSONObject(strContent.toString());
                    JSONArray countriesObj = responseObj.getJSONArray("countries");


                    Toast toast = Toast.makeText(getActivity(),
                            "Downloading of data just finished", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    @Override
    protected void setUp(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListiner) {
            mListener = (FragmentInteractionListiner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDestroy();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
      /*  if("Joseph Young".toLowerCase().trim().contains(query.toLowerCase().trim()) || "People of the philippines".toLowerCase().trim().contains(query.toLowerCase().trim())){
            expandableListDetail = ExpandableListDataPump.getData();
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            for (int i = 0; i < expandableListTitle.size(); i++) {
                Log.e("expandableListDetail",expandableListTitle.get(i));

            }
            expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Expanded.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }
        else if("Jude Kevin".toLowerCase().trim().contains(query.toLowerCase().trim()) || "Thomas".toLowerCase().trim().contains(query.toLowerCase().trim())){
            expandableListDetail = ExpandableListDataPump.getDataCase3();
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            for (int i = 0; i < expandableListTitle.size(); i++) {
                Log.e("expandableListDetail",expandableListTitle.get(i));

            }
            expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Expanded.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }
        else if("Rei Mark".toLowerCase().trim().contains(query.toLowerCase().trim()) || "Diodel Gerona".toLowerCase().trim().contains(query.toLowerCase().trim())){
            expandableListDetail = ExpandableListDataPump.getDataCase2();
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            for (int i = 0; i < expandableListTitle.size(); i++) {
                Log.e("expandableListDetail",expandableListTitle.get(i));

            }
            expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Expanded.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getActivity(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }*/
        return false;
    }
    public void clearClipBoard(){
        clip = android.content.ClipData.newPlainText("Clip","");
        clipboard.setPrimaryClip(clip);
    }
    public void copyHighLightedText(){
        if(!TextUtils.isEmpty(clipboard.getPrimaryClip().toString())){
            CharSequence item = clipboard.getPrimaryClip().getItemAt(0).coerceToText(getContext());
            String copiedText = "Case id :"+caseId+"\nPage no."+page+"\nCopied Text :"+item;
            String fieldCopiedText = item.toString();
            Log.e(TAG,copiedText);
            presenter.storeHighlightedTextRequest(caseId,userId,fieldCopiedText);
//            Toast.makeText(getActivity(),copiedText, Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isClipboardEmpty(){
//        if(!TextUtils.isEmpty(clipboard.getPrimaryClip().toString())){
//            String copiedText = "Case id :"+caseId+"\nPage no."+page+"\nCopied Text :"+clipboard.getPrimaryClip();
//            clip = android.content.ClipData.newPlainText("Clip",clipboard.setPrimaryClip(clip));
//            Toast.makeText(getActivity(), clip.toString(), Toast.LENGTH_SHORT).show();
//
//        }
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        CommonUtils.showOrHideKeyboard(editsearch,getActivity(),true);
        presenter.getSearchCase(newText);
        return false;
    }

    @Override
    public void querySearchResultResponse(final List<Case> cases) {
        lvResult.setVisibility(View.VISIBLE);
        if(cases.size() == 0 || cases.isEmpty()){
            Case noMatch = new Case();
            noMatch.setId(-0);
            cases.add(noMatch);
            adapter.setResultList(cases);
            lvResult.setAdapter(adapter);
        }
        else {
            adapter.setResultList(cases);
            lvResult.setAdapter(adapter);
            lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    Case acase = (Case) parent.getItemAtPosition(position);
                    caseId = acase.getId()+"";
                        expandableListDetail = ExpandableListDataPump.getData(acase);
                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);
                        expandableListView.expandGroup(0);
                    CommonUtils.showOrHideKeyboard(view,getActivity(),false);
                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                            @Override
                            public void onGroupExpand(int groupPosition) {
                                Toast.makeText(getActivity(),
                                        expandableListTitle.get(groupPosition) + " List Expanded.",
                                        Toast.LENGTH_SHORT).show();
                                CommonUtils.showOrHideKeyboard(view,getActivity(),false);
                            }
                        });

                        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
                                Toast.makeText(getActivity(),
                                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                    lvResult.setVisibility(View.GONE);
                    }
            });
        }
    }

    @Override
    public void highLightRequestResponse(ErrorParser errorParser) {
        if(!TextUtils.isEmpty(errorParser.getMsg()))
            showSnackBar(errorParser.getMsg());
        else if(!TextUtils.isEmpty(errorParser.getMsg()))
            showSnackBar(errorParser.getMessage());
        else
            showSnackBar(getString(R.string.error_text));
    }

    @Override
    public void onError(String message) {
        super.onError(message);
        List<Case> emptyList = new ArrayList<>();
        adapter.setResultList(emptyList);
        lvResult.setAdapter(adapter);
    }
    public static void setDownloadManager(Context context){
        Uri Download_Uri = Uri.parse("https://textfiles.com/apple/6502.bugs.txt");
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("My Data Download");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Android Data download using DownloadManager.");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"CountryList.txt");

        //Enqueue a new download and same the referenceId
        downloadReference = downloadManager.enqueue(request);
    }
    private void checkStatus(Cursor cursor){

        //column for status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch(status){
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + filename;
                break;
        }


        Toast toast = Toast.makeText(getActivity(),
                statusText + "\n" +
                        reasonText,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();

    }

}

