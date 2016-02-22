package modified.dobjanschi.a.pattern.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modified.dobjanschi.a.pattern.activity.MainActivity;
import modified.dobjanschi.a.pattern.api.API;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.model.RequestItem;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * @author Rustem
 */
public class MainService extends Service {

    private final String TAG = "MainService";

    public static void start(@NonNull Context context) {
        context.startService(new Intent(context, MainService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final RequestItem requestItem = RequestsTable.getNewItem(getBaseContext());

        Toast.makeText(MainService.this, "Service started to work", Toast.LENGTH_SHORT).show();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.HH_URL)
                    .build();

        requestItem.setStatus(MainActivity.STATUS_IN_PROGRESS);
        RequestsTable.update(getBaseContext(), requestItem);

        API rest_api = restAdapter.create(API.class);
        rest_api.getVacancies(MainActivity.VACANCY_NAME, MainActivity.VACANCY_AREA, new retrofit.Callback<Response>() {

            @Override
            public void success(Response v, Response response2) {

                        TypedInput body = v.getBody();
                        String line = "";

                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                            line = reader.readLine();
                            Log.d(TAG, line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        requestItem.setResponse(line);
                        requestItem.setStatus("RECEIVED");
                        Toast.makeText(MainService.this, "Server recived response", Toast.LENGTH_SHORT).show();

                        RequestsTable.clear(getBaseContext());
                        RequestsTable.save(getBaseContext(), requestItem);
                        getBaseContext().getContentResolver().notifyChange(RequestsTable.URI, null);


                }

            @Override
            public void failure(RetrofitError error) {
                    String message = error.getMessage();
                    Log.d(TAG, message);
                    Toast.makeText(MainService.this, message, Toast.LENGTH_SHORT).show();
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