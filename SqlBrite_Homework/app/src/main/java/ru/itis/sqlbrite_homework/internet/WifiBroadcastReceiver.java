package ru.itis.sqlbrite_homework.internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        Log.d(TAG, "status=" + status);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

        if(status.equals(NetworkUtil.TYPE_WIFI)){
            setPerson(context);
        }
    }

    private void setPerson(Context mContext){
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);

        Subscription subscription = databaseHelper.getPeopleList()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Person>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error getting the people " + e);
                    }

                    @Override
                    public void onNext(List<Person> person) {
                        Log.d(TAG, "person.size()=" + person.size());
                    }
                });
    }

}
