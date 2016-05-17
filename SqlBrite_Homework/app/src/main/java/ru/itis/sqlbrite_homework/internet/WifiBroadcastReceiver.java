package ru.itis.sqlbrite_homework.internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.model.Person;
import ru.itis.sqlbrite_homework.ui.activity.MainActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;

/**
 * Created by yasina on 17.05.16.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "WifiBroadcastReceiver";

    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    @Override
    public void onReceive(Context context, Intent intent) {

        int status_id = NetworkUtil.getConnectivityStatus(context);
        if(status_id == NetworkUtil.TYPE_WIFI){

            MainActivity.receiver.send(STATUS_FINISHED, Bundle.EMPTY);
        }else {
            MainActivity.receiver.send(STATUS_ERROR, Bundle.EMPTY);
        }
    }



}
