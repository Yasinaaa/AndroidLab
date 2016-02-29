package modified.dobjanschi.a.pattern.api;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import modified.dobjanschi.a.pattern.activity.MainActivity;
import modified.dobjanschi.a.pattern.service.model.Vacancy;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by yasina on 13.02.15.
 */
public class API {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String type = "/vacancies";
    private static final String TAG = "API";


    private static OkHttpClient client = new OkHttpClient();

    public static Call post(String url,Callback callback){

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static String getPath(int area, String name){
        String path = null;
        try {
            path = MainActivity.HH_URL + type + "?" + "text=" +
                    URLEncoder.encode(name, "UTF-8")
                    + "&" + "area=" + area;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, path);
        return path;
    }

}
