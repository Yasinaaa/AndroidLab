package modified.dobjanschi.a.pattern;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import modified.dobjanschi.a.pattern.activity.LoadedContentActivity;
import modified.dobjanschi.a.pattern.callback.IntentServiceResultReceiver;
import modified.dobjanschi.a.pattern.db.DatabaseObserver;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.model.Vacancy;
import modified.dobjanschi.a.pattern.service.MainService;

public class MainActivity extends AppCompatActivity {

    public static final String HH_URL = "https://api.hh.ru";

    public static final String VACANCY_NAME = "Программист стажер";
    public static final int VACANCY_AREA = 88;

    public IntentServiceResultReceiver receiver;

    private final ContentObserver mMessagesObserver = new DatabaseObserver() {

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            LoadedContentActivity.start(MainActivity.this);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LoadedContentActivity.start(MainActivity.this);
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(RequestsTable.URI, true, mMessagesObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            // clear receiver to avoid leaks.
            receiver.setReceiver(null);
        }

        getContentResolver().unregisterContentObserver(mMessagesObserver);
    }


    private void startService() {
        MainService.start(this);
    }



}