package ru.itis.lectures.unittesting.powermock;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

/**
 * @author Artur Vasilov
 */
public class DataLoadedProcessor {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    @Response
    @NonNull
    public static String processDataLoaded(@NonNull String data) {
        if (data.contains("response")) {
            OttoBus.get().post(DataLoadedEvent.produce());
            return SUCCESS;
        }
        return ERROR;
    }

    @StringDef({SUCCESS, ERROR})
    private @interface Response {
    }
}

