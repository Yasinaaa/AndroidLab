package modified.dobjanschi.a.pattern.activity;

import android.database.ContentObserver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import modified.dobjanschi.a.pattern.R;
import modified.dobjanschi.a.pattern.callback.IntentServiceResultReceiver;
import modified.dobjanschi.a.pattern.db.DatabaseObserver;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.MainService;
import modified.dobjanschi.a.pattern.service.model.RequestItem;

public class MainActivity extends AppCompatActivity {

    public static final String HH_URL = "https://api.hh.ru";

    public static final String VACANCY_NAME = "Программист стажер";
    public static final int VACANCY_AREA = 88;

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_IN_PROGRESS = "N_PROGRESS";
    public static final String STATUS_RECEIVED = "RECEIVED";


    public IntentServiceResultReceiver receiver;
    private View throbberView;

    private final ContentObserver mMessagesObserver = new DatabaseObserver() {

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            showThrobber(false);
            LoadedContentActivity.start(MainActivity.this);
        }


    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        throbberView = findViewById(R.id.throbber_layout);
        findViewById(R.id.service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestItem requestItem = new RequestItem(VACANCY_NAME + "_" + VACANCY_AREA, STATUS_NEW);
                RequestsTable.save(getBaseContext(), requestItem);
                startService();
            }
        });

    }

    private void showThrobber(boolean isVisible) {
        throbberView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
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
        showThrobber(true);
        MainService.start(this);
    }



}