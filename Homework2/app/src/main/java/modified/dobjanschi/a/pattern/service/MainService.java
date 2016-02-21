package modified.dobjanschi.a.pattern.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modified.dobjanschi.a.pattern.MainActivity;
import modified.dobjanschi.a.pattern.api.API;
import modified.dobjanschi.a.pattern.db.DatabaseUtils;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.model.RequestItem;
import modified.dobjanschi.a.pattern.service.model.Vacancy;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * @author Rustem
 */
public class MainService extends Service {

    private final String TAG = "MainService";

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

   // private VacanicesRepository mVacancyRepository;

    public static void start(@NonNull Context context) {
        // start a new server request.
        context.startService(new Intent(context, MainService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MainService.class.getName(), "service started...");
        Toast.makeText(MainService.this, "Service started", Toast.LENGTH_SHORT).show();


            RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.HH_URL)
                    .build();

           /* mVacancyRepository = new VacanicesRepository(getApplicationContext());
            mVacancyRepository.dropTable();*/

            API rest_api = restAdapter.create(API.class);
            rest_api.getVacancies(MainActivity.VACANCY_NAME, MainActivity.VACANCY_AREA, new retrofit.Callback<Response>() {
                @Override
                public void success(Response v, Response response2) {

                        RequestItem requestItem = new RequestItem("request1");
                        TypedInput body = v.getBody();
                        String line = "";

                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                            line = reader.readLine();
                            Log.d(TAG, line);
                            Toast.makeText(getBaseContext(), line, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        requestItem.setResponse(line);
                        RequestsTable.clear(getBaseContext());
                        RequestsTable.save(getBaseContext(), requestItem);
                        getBaseContext().getContentResolver().notifyChange(RequestsTable.URI, null);


                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, error.getMessage());
                }
            });


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
}