package modified.dobjanschi.a.pattern.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import modified.dobjanschi.a.pattern.R;
import modified.dobjanschi.a.pattern.activity.MainActivity;
import modified.dobjanschi.a.pattern.api.API;
import modified.dobjanschi.a.pattern.db.tables.RequestsTable;
import modified.dobjanschi.a.pattern.service.model.RequestItem;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author Rustem
 */
public class MainService extends Service {

    private final String TAG = "MainService";
    private Subscription subscribe;
    private int max_retries = 2;
    private int now_attempt = 0;

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
        final RequestItem requestItem = RequestsTable.getItem(getBaseContext(), MainActivity.STATUS_NEW);

        requestItem.setStatus(MainActivity.STATUS_IN_PROGRESS);
        RequestsTable.update(getBaseContext(), requestItem);
        //
        subscribe = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(final Subscriber<? super Response> subscriber) {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(new Request.Builder().url(API.getPath(MainActivity.VACANCY_AREA, MainActivity.VACANCY_NAME)).build()).execute();
                    if (response.isSuccessful()) {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception("error"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .retryWhen(new RetryWithDelay(3, 2000))

                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        getContentResolver().notifyChange(RequestsTable.URI, null);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        String message = throwable.getMessage();
                        Log.d(TAG, message);

                        requestItem.setStatus(MainActivity.STATUS_ERROR);
                        RequestsTable.clear(MainService.this);
                        RequestsTable.save(MainService.this, requestItem);
                        Log.d(TAG, "in fun " + throwable.getMessage());

                        onCompleted();

                    }

                    @Override
                    public void onNext(Response response) {

                        try {
                            String line = response.body().string();
                            requestItem.setResponse(line);
                            Log.d(TAG, line);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        requestItem.setStatus("RECEIVED");
                        RequestsTable.clear(MainService.this);
                        RequestsTable.save(MainService.this, requestItem);

                    }
                })
        ;


        //
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    public class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {

            return attempts
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (++retryCount < maxRetries) {
                                // When this Observable calls onNext, the original
                                // Observable will be retried (i.e. re-subscribed).
                                Log.d(TAG, "retryCount=" + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }else {

                                // Max retries hit. Just pass the error along.
                                return Observable.error(throwable);
                            }
                        }
                    });
        }
    }
}