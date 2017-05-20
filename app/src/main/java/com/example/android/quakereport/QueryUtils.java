package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
     * Helper methods related to requesting and receiving earthquake data from USGS.
     */
    public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtils() {
        }

        /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */
        public static List<Earthquake> extractEarthquakes(String stringURL) {



            //Create a URL object
            URL url = createURL(stringURL);

            //Perform HTTP request and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHTTPRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            List<Earthquake> earthquakes = extractFeaturesFromJSON(jsonResponse);
            return earthquakes;
        }


        /**
         * Returns new URL object from the given string URL.
         */
        private static URL createURL(String stringURL) {
           URL url = null;
            try {
                url = new URL(stringURL);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error with creating URL: ", e);
            }
            return url;
        }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";
        //If the URL is null, return early
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode() );
            }
        }catch (IOException e) {
            Log.e(LOG_TAG, "Error making the HTTP request: ", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Earthquake} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static List<Earthquake> extractFeaturesFromJSON(String earthquakeJSON){
        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject jsonRootObject = new JSONObject(earthquakeJSON);

            //Get the instance of JSON array that contains features (earthquakes)
            JSONArray earthquakeArray = jsonRootObject.getJSONArray("features");

            //Loop through each feature in the array
            for(int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject jsonObject = earthquakeArray.getJSONObject(i);

                JSONObject properties = jsonObject.getJSONObject("properties");

                String url = properties.getString("url");

                //Extract mag for magnitude
                double mag = properties.getDouble("mag");
                //Extract place for location
                String place = properties.getString("place");

                //Extract time for date of earthquake
                long milliseconds = properties.getLong("time");

                Date date = new Date(milliseconds);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                String dateToDisplay = dateFormat.format(date);

                SimpleDateFormat timeFormat = new SimpleDateFormat("K:mm a");
                String timeToDisplay = timeFormat.format(date);


                //Create earthquake object from above mag, place, and time
                Earthquake earthquake = new Earthquake(mag, place, timeToDisplay, dateToDisplay, url);
                //Add earthquake to list of earthquakes
                earthquakes.add(earthquake);


            }



        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;

    }

}