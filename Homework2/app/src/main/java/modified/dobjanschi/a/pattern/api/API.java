package modified.dobjanschi.a.pattern.api;

import modified.dobjanschi.a.pattern.service.model.Vacancy;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by yasina on 13.02.15.
 */
public interface API {

    @GET("/vacancies")
    void getVacancies(@Query("text") String text, @Query("area") int area, Callback<Response> responseCallback);

}
