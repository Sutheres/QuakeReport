package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by suthe_000 on 10/6/2016.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

   private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        //Dont perform the request if there are no URLs, or the first URL is null
        if(mUrl == null) {
            return null;
        }

        //Perform the HTTP request for earthquake data and receive a response
        List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(mUrl);
        return earthquakes;
    }
}
