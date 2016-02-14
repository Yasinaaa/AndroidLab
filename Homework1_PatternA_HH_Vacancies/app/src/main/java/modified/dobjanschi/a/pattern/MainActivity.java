package modified.dobjanschi.a.pattern;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import modified.dobjanschi.a.pattern.adapter.VacanciesAdapter;
import modified.dobjanschi.a.pattern.db.VacanicesRepository;
import modified.dobjanschi.a.pattern.model.Vacancy;

public class MainActivity extends AppCompatActivity implements BgProcessingResultReceiver.Receiver {

    public static final String HH_URL = "https://api.hh.ru";

    public static final String VACANCY_NAME = "Программист стажер";
    public static final int VACANCY_AREA = 88;

    public BgProcessingResultReceiver receiver;
    private RecyclerView vacanciesRecycleView;
    private VacanciesAdapter mAdapter;
    private VacanicesRepository mVacancyRepository;
    private List<Vacancy.Item> mVacancyList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vacanciesRecycleView = (RecyclerView) findViewById(R.id.vacancies_list_recycler_view);
        vacanciesRecycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        startLoading();

    }

    private void startLoading() {
        // register a receiver for IntentService broadcasts
        receiver = new BgProcessingResultReceiver(new Handler());
        receiver.setReceiver(this);
        // start IntentService
        final Intent intent = new Intent(Intent.ACTION_SYNC, null,
                this, BgProcessingIntentService.class);
        // optional: send Extra to IntentService
        // intent.putExtra("name", "value");
        intent.putExtra("receiver", receiver);
        intent.putExtra("command", "query");
        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case BgProcessingIntentService.STATUS_RUNNING:
                //show progress
                Toast.makeText(this, "Running", Toast.LENGTH_SHORT).show();
                break;
            case BgProcessingIntentService.STATUS_FINISHED:

                mVacancyRepository = new VacanicesRepository(getBaseContext());
                mVacancyList = mVacancyRepository.getAllVacancies();

                mAdapter = new VacanciesAdapter(mVacancyList);
                vacanciesRecycleView.setAdapter(mAdapter);

                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                break;
            case BgProcessingIntentService.STATUS_ERROR:
                // handle the error;
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            receiver.setReceiver(null); // clear receiver to avoid leaks.
        }
    }
}