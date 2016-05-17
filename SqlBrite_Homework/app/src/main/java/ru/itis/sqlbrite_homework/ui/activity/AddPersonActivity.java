package ru.itis.sqlbrite_homework.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.DataManager;
import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.local.Db;
import ru.itis.sqlbrite_homework.data.model.Person;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddPersonActivity extends AppCompatActivity {

    @InjectView(R.id.edit_text_name)
    EditText mNameEditText;

    private static final String TAG = "AddPersonActivity";
    private DataManager mDataManager;
    private List<Subscription> mSubscriptions;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSubscriptions = new ArrayList<>();
        mDataManager = new DataManager(this);
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription subscription : mSubscriptions) subscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                savePerson();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void savePerson() {
      String name = mNameEditText.getText().toString();
      final DatabaseHelper databaseHelper = new DatabaseHelper(this);


      if (!name.isEmpty()) {
            mSubscriptions.add(AppObservable.bindActivity(this,
                    mDataManager.savePerson(new Person(name)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Person>() {
                            @Override
                            public void onCompleted() {
                                startActivity(new Intent(mContext, MainActivity.class));
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "There was a error saving the person " + e);
                            }

                            @Override
                            public void onNext(Person person) { }
                        }));
        } else {
            Toast.makeText(this, getString(R.string.toast_empty_name), Toast.LENGTH_SHORT).show();
        }
    }

}
