package modified.dobjanschi.a.pattern;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import modified.dobjanschi.a.pattern.api.API;
import modified.dobjanschi.a.pattern.db.VacanicesRepository;
import modified.dobjanschi.a.pattern.model.Vacancy;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Rustem
 */
public class BgProcessingIntentService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private VacanicesRepository mVacancyRepository;

    public BgProcessingIntentService() {
        super(BgProcessingIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(BgProcessingIntentService.class.getName(), "service started...");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String command = intent.getStringExtra("command");
        final Bundle bundle = new Bundle();
        if (command.equals("query")) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.HH_URL)
                    .build();

            mVacancyRepository = new VacanicesRepository(getApplicationContext());
            mVacancyRepository.dropTable();

            API rest_api = restAdapter.create(API.class);
            rest_api.getVacancies(MainActivity.VACANCY_NAME, MainActivity.VACANCY_AREA, new retrofit.Callback<Vacancy>() {
                @Override
                public void success(Vacancy v, Response response2) {

                    for (Vacancy.Item i : v.items) {
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

                        mVacancyRepository.add(i);
                    }
                    receiver.send(STATUS_FINISHED, bundle);
                }

                @Override
                public void failure(RetrofitError error) {
                    receiver.send(STATUS_ERROR, bundle);
                    Log.d(BgProcessingIntentService.class.getName(), "error");
                }
            });

        }
        Log.d(BgProcessingIntentService.class.getName(), "service stopping...");
        this.stopSelf();
    }
}