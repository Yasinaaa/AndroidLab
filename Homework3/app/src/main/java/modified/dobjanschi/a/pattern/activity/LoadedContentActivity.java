package modified.dobjanschi.a.pattern.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import modified.dobjanschi.a.pattern.R;
import modified.dobjanschi.a.pattern.adapter.VacanciesAdapter;
import modified.dobjanschi.a.pattern.db.RequestItemContract;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.model.Vacancy;


/**
 * @author Rustem
 */
public class LoadedContentActivity extends AppCompatActivity {

    private RecyclerView vacanciesRecycleView;
    private VacanciesAdapter mAdapter;
    private Vacancy mVacancy;
    private List<Vacancy.Item> mVacancyList;
    private View throbberView;
    private TextView tvError;

    public static void start(Activity activity) {
        ActivityCompat.startActivity(activity, new Intent(activity, LoadedContentActivity.class), null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaded_content);

        vacanciesRecycleView = (RecyclerView) findViewById(R.id.vacancies_list_recycler_view);
        vacanciesRecycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        throbberView = findViewById(R.id.throbber_layout);
        tvError = (TextView) findViewById(R.id.tv_error);
        tvError.setEnabled(false);
        showThrobber(true);

        mVacancyList = new ArrayList<Vacancy.Item>();
    }

    private void showThrobber(boolean isVisible) {
        throbberView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = getContentResolver().query(RequestsTable.URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String status = cursor.getString(cursor.getColumnIndex(RequestItemContract.RequestColumns.STATUS));
            showThrobber(false);

            if (!status.equals(MainActivity.STATUS_ERROR)){
                String json = cursor.getString(cursor.getColumnIndex(RequestItemContract.RequestColumns.RESPONSE));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                mVacancy = gson.fromJson(json, Vacancy.class);
                for (Vacancy.Item i : mVacancy.items) {
                    Log.d("TAG", i.name);
                    Log.d("TAG", i.employer.name);
                    try {
                        Log.d("TAG", i.snippet.requirement);
                    } catch (java.lang.NullPointerException e) {
                        i.snippet.requirement = "None";
                    }
                    try {
                        Log.d("TAG", i.snippet.responsibility);
                    } catch (java.lang.NullPointerException e) {
                        i.snippet.responsibility = "None";
                    }

                    mVacancyList.add(i);
                }
                mAdapter = new VacanciesAdapter(mVacancyList);
                vacanciesRecycleView.setAdapter(mAdapter);
            }else {
                tvError.setEnabled(true);
            }

            getContentResolver().delete(RequestsTable.URI, RequestItemContract.RequestColumns.STATUS + "=?",
                    new String[]{MainActivity.STATUS_RECEIVED});
            cursor.close();
        }


    }
}