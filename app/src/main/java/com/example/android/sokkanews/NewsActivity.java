/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sokkanews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = NewsActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
        private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=football&api-key=08a78522-fca4-4de1-893f-f2da1b01c331";

    /** Adapter for the list of earthquakes */
    private NewsAdapter mAdapter;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**TextView displayed when the ListView is empty*/
    private TextView mEmptyStateTextView;

    /**The Loading indicator*/
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sokka_news_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        progressBar = findViewById(R.id.progress);


        //Get a reference to the Loader Manager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();


        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //Initialise the loader. Pass in the int ID constant defined above and pass in null for
            //bundle. Pass in this activity for the LoaderCallbacks parameter ((which is valid)
            //because ths activity implements the LoaderCallbacks interface.
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        //Create a new loader for the given URL
        return new NewsLoader(this, USGS_REQUEST_URL);

    }



    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        //Clear the adapter of previous earthquake data
        mAdapter.clear();
        Log.i(LOG_TAG,"Method onLoadFinished created");
        //Set an empty state to display "No news found."
        mEmptyStateTextView.setText(R.string.no_news);


        progressBar.setVisibility(View.GONE);

        //If there is a valid list of {@link News}, then add them to the adapter's data set.
        //This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
        }

        @Override
        public void onLoaderReset(Loader<List<News>> loader) {
            Log.i(LOG_TAG,"Method onLoadFinished called");
            // Loader reset, so we can clear out existing data
            mAdapter.clear();
        }

   }
