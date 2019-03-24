package com.example.ryan.ign_code_foo_2019_android_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>> {
    View rootView;

    private String REQUEST_URL = "https://ign-apis.herokuapp.com/content";

    private String LOG_TAG = "Video Fragment";

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seeing if there is internet connectivity..code from official Android Website about connecting to the Internet
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Toast.makeText(getContext(), getContext().getString(R.string.InternetErrorMessage), Toast.LENGTH_LONG).show();

        } else {
            Log.v(LOG_TAG, "It has internet, About to execute Loader");
            getActivity().getSupportLoaderManager().restartLoader(1, null, this).forceLoad();

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_article, container, false);

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v(LOG_TAG, "Calling the Article Loader Now");


        // Parsing the basic String URL
        Uri uriBase = Uri.parse(REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = uriBase.buildUpon();


        // The URI that has my results: https://ign-apis.herokuapp.com/content?startIndex=8/u0026count=5

        //Build the URI appropriately.
        uriBuilder.appendQueryParameter("startIndex", "8");
        uriBuilder.appendQueryParameter("count", "10");

        //Checking to see if I the API builds the URI appropriately.
        Log.v(LOG_TAG, "Here is the URI being built " + uriBuilder.toString());


        return new ArticleLoader(getContext(), uriBuilder.toString(), "VideoFragment");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> data) {

        Log.v(LOG_TAG, "We done getting data from the internet.");

        ListView listView = rootView.findViewById(R.id.article_List_View);


        CustomListAdapter theArticleAdapter = new CustomListAdapter(getContext(), data, "VideoFragment");
        listView.setOnItemClickListener(mMessageClickedHandler);
        listView.setAdapter(theArticleAdapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {

    }

    // Creating an OnClickListener for my AdapterView (ListView) Found this on the Android Website https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.
    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Log.v(LOG_TAG, "Someone click something on the List View");

            //Get slug  from "URL", build URL, send intent..and with building URl, add the current data
            Article currentArticle = (Article) parent.getItemAtPosition(position);

            String baseURL = "https://www.ign.com/videos";
            String dateURL = currentArticle.getaURLDate();
            String slugURL = currentArticle.getaURL();
            String combineURL = baseURL + dateURL + slugURL;


            Uri webView = Uri.parse(combineURL);
            Intent intent = new Intent(Intent.ACTION_VIEW, webView);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }


        }

    };
}
