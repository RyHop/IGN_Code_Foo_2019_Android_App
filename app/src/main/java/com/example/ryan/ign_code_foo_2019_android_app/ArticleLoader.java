package com.example.ryan.ign_code_foo_2019_android_app;

import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ArticleLoader extends android.support.v4.content.AsyncTaskLoader<List<Article>> {
    private String articleURL;
    private String LOG_TAG = "NewArticleLoader";
    private String fragmentCaller;


    public ArticleLoader(Context context, String URL, String fragmentCaller) {
        super(context);
        Log.v(LOG_TAG, "ArticleLoader is initialized");
        this.articleURL = URL;
        this.fragmentCaller = fragmentCaller;

    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        QueryUtils internetMessenger = new QueryUtils();
        Log.v(LOG_TAG, "Article is calling loadInBackground");
        URL url = createUrl(articleURL);
        Log.v(LOG_TAG, "Created URL");

        return internetMessenger.getDataFromInternet(url, fragmentCaller);
    }


    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

}


