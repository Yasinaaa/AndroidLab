package ru.itis.sqlbrite_homework.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.DataManager;
import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.model.Person;
import ru.itis.sqlbrite_homework.internet.WifiBroadcastReceiver;
import ru.itis.sqlbrite_homework.ui.adapter.PersonHolder;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class MainActivity extends AppCompatActivity{

    @InjectView(R.id.text_no_people)
    TextView mNoPeopleText;

    @InjectView(R.id.recycler_people)
    RecyclerView mPersonRecycler;

    private static final String TAG = "MainActivity";
    private DataManager mDataManager;
    private List<Subscription> mSubscriptions;
    private ArrayList<Person> mCurrentPeople;
    private EasyRecyclerAdapter<Person> mEasyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mCurrentPeople = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        mDataManager = new DataManager(this);

        init();
      //  getPeople();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription subscription : mSubscriptions) subscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, AddPersonActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        canConnect();

        mPersonRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEasyRecyclerAdapter = new EasyRecyclerAdapter<>(this, PersonHolder.class, mCurrentPeople);
        mPersonRecycler.setAdapter(mEasyRecyclerAdapter);

    }
    private void canConnect()
    {
        BroadcastReceiver broadcastReceiver = new WifiBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getBaseContext().registerReceiver(broadcastReceiver, intentFilter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


      /*  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d(TAG, "networkInfo.getType()=" + networkInfo.getType());
        Log.d(TAG, "ConnectivityManager.TYPE_WIFI=" + ConnectivityManager.TYPE_WIFI);
        Log.d(TAG, "ConnectivityManager.TYPE_MOBILE=" + ConnectivityManager.TYPE_MOBILE);*/
       /* if(networkInfo != null)
        {
            if(networkInfo.isConnected())
            {
                if((networkInfo.getType() == ConnectivityManager.TYPE_WIFI) ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && !wifiOnly))
                {
                    canConnect = true;
                }
            }
        }*/

    }

    /*public  void getPeople() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

      mSubscriptions.add(AppObservable.bindActivity(this, mDataManager.getPeople())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error getting the people " + e);
                    }

                    @Override
                    public void onNext(Person person) {
                        if (!mCurrentPeople.contains(person)) {
                            mCurrentPeople.add(person);
                            mEasyRecyclerAdapter.notifyItemInserted(mCurrentPeople.size());
                            if (mNoPeopleText.getVisibility() == View.VISIBLE) {
                                mNoPeopleText.setVisibility(View.GONE);
                            }
                        }
                    }
                }));
    }*/

}
