package com.gru.cajaaplicacionestics.auxiliares;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;

/**
 * Created by guill on 22/11/2017.
 */

public class AnalitycsAplication extends Application
{
    private Tracker mTracker; //para google analitycs


    synchronized public Tracker getDefaultTracker()
    {
        if(mTracker==null)
        {
            GoogleAnalytics analytics= GoogleAnalytics.getInstance(this);
            mTracker= analytics.newTracker(R.xml.analytics_tracker);
        }
        return mTracker;
    }
}
