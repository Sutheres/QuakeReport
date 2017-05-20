package com.example.android.quakereport;

/**
 * Created by suthe_000 on 8/31/2016.
 */
public class Earthquake {

    // Holds the magnitude of the earthquake
    private double mMagnitude;
    // Holds location/city of the earthquake
    private String mCity;
    // Holds date that the earthquake
    private String mDate;
    // Holds time of earthquake
    private String mTime;
    // Holds webpage for current earthquake
    private String mUrl;

    /* Constructors */

    public Earthquake(double magnitude, String city, String time, String date, String url) {
        mMagnitude = magnitude;
        mCity = city;
        mTime = time;
        mDate = date;
        mUrl = url;
    }


    /******** METHODS *********/
    public double getMagnitude() {return mMagnitude;}

    public String getCity() {return mCity;}

    public String getDate() {return mDate;}

    public String getTime() {return mTime;}

    public String getUrl() {return mUrl;}
}
