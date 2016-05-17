package ru.itis.sqlbrite_homework.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.DataManager;
import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.model.Person;
import ru.itis.sqlbrite_homework.internet.BgProcessingResultReceiver;
import ru.itis.sqlbrite_homework.internet.WifiBroadcastReceiver;
import ru.itis.sqlbrite_homework.ui.adapter.PersonAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements BgProcessingResultReceiver.Receiver{

    @InjectView(R.id.text_no_people)
    TextView mNoPeopleText;

    @InjectView(R.id.recycler_people)
    RecyclerView mPersonRecycler;

    private static final String TAG = "MainActivity";
    public static boolean wifiConnected;
    private DataManager mDataManager;
    private List<Subscription> mSubscriptions;
    private List<Person> mCurrentPeople;
    private PersonAdapter mPersonAdapter;
    public static BgProcessingResultReceiver  receiver;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mCurrentPeople = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        mDataManager = new DataManager(this);
        mDatabaseHelper = new DatabaseHelper(getBaseContext());

        init();
        startLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription subscription : mSubscriptions) subscription.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (wifiConnected) setPersonList();
    }

    private void setPersonList(){

        final Activity activity = this;

        Subscription subscription = mDatabaseHelper.getPeople()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Person person) {
                        mCurrentPeople.add(person);
                        mPersonAdapter = new PersonAdapter(mCurrentPeople);
                        mPersonAdapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
                            @Override
                            public void onRemoveClick(Person viewModel, int position) {
                                mSubscriptions.add(AppObservable.bindActivity(activity, mDataManager.removePerson(viewModel))
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
                                                setPersonList();
                                            }
                                        }));
                            }

                            @Override
                            public void onUpdateClick(Person viewModel) {
                                Intent i = new Intent(activity, AddPersonActivity.class);
                                i.putExtra("viewModel", viewModel);
                                startActivity(i);
                            }
                        });
                        mPersonRecycler.setAdapter(mPersonAdapter);

                        if (mNoPeopleText.getVisibility() == View.VISIBLE) {
                            mNoPeopleText.setVisibility(View.GONE);
                        }
                    }
                });
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
        /*mEasyRecyclerAdapter = new EasyRecyclerAdapter<>(this, PersonHolder.class, mCurrentPeople);
        mPersonRecycler.setAdapter(mEasyRecyclerAdapter);*/
    }

    private void startLoading() {
        receiver = new BgProcessingResultReceiver(new Handler());
        receiver.setReceiver(this);
    }

    private void canConnect()
    {
        BroadcastReceiver broadcastReceiver = new WifiBroadcastReceiver();


        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getBaseContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case WifiBroadcastReceiver.STATUS_FINISHED:
                setPersonList();
                break;

            case WifiBroadcastReceiver.STATUS_ERROR:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
