package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by suthe_000 on 8/31/2016.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private int mMagnitudeColor;

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude
        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        //Create a decimal format of "0.0" in case we receive a magnitude with an
        //extra decimal number i.e. ) 0.00
        DecimalFormat formatter = new DecimalFormat("0.0");
        // Apply the format to the current earthquake's magnitude
        String output = formatter.format(currentEarthquake.getMagnitude());

        //Set the proper background on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable
        GradientDrawable magnitudeCircle = (GradientDrawable)magTextView.getBackground();

        // Get the appropriate background color based on magnitude of current earthquake
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        // Get the magnitude from the current Earthquake object and
        // set this text on the name TextView
        magTextView.setText(output);

        // Find the TextView in the list_item.xml layout with the ID offset and primary
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.offset);
        TextView cityTextView2 = (TextView) listItemView.findViewById(R.id.primary);
        String city = currentEarthquake.getCity();
        if(city.contains(",")) {
            String[] locations = city.split(",");
            String offsetLocation = locations[0];
            String primaryLocation = locations[1];
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            cityTextView.setText(offsetLocation);
            cityTextView2.setText(primaryLocation);
        } else {
            String offsetLocation = "Near the";
            String primaryLocation = city;
            cityTextView.setText(offsetLocation);
            cityTextView2.setText(primaryLocation);
        }
        //Find the TextView in the list_item.xml layout with the ID time
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Get the time from the current Earthquake object and
        // set this text on the TextView
        timeTextView.setText(currentEarthquake.getTime());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        dateTextView.setText(currentEarthquake.getDate());



        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    public int getMagnitudeColor(double magnitude) {

        int intMag = (int) magnitude;


        switch (intMag) {
            case 0:
            case 1:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            case 10:
                mMagnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;

        }

        return mMagnitudeColor;
    }

}
