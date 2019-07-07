package com.menginar.foursquare.data.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.menginar.myflamingo.base.baselog.LogType;
import com.menginar.myflamingo.base.baselog.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Internet bağlantı kontorlu için
 * */

@Singleton
public class NetworkManager {

    private Logger logger;
    private Context context;

    @Inject
    public NetworkManager(Logger logger, Context context) {
        this.logger = logger;
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean checkNetworkConnection() {

        if (context == null)
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            if (capabilities == null) {
                return false;
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                if (logger != null)
                    logger.log(LogType.INFO, "TYPE_WIFI", "Wifi Bağlantısı Var.");
                return true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                if (logger != null)
                    logger.log(LogType.INFO, "TYPE_MOBILE", "Mobile Bağlantısı Var.");
                return true;
            }
        } else {
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected()) {
                if (logger != null)
                    logger.log(LogType.INFO, "NETWORK_CONNETION", "Network Bağlantısı Yok.");

                return false;
            }

            if (logger != null) {
                logger.log(LogType.INFO, "NETWORK_CONNETION", "Network Bağlantısı Var.");
            }

            return true;
        }

        return false;
    }
}
