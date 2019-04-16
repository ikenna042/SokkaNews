package com.example.android.sokkanews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /**Tag for log messages*/
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**Query URL*/
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    /**
     * This is on background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        //Perform the network request, parse the response, and extract a list of soccer news.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
