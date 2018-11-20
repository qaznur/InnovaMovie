package com.tutorial.nura.innovamovie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nura on 20.11.2018.
 */
public class ConnectionUtil {

    public static boolean hasConnection(final Context context) {
        return hasNetwork(context);
    }

    private static boolean hasNetwork(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}
