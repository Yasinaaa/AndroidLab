package ru.itis.lectures.unittesting.powermock;

import android.support.annotation.NonNull;

import com.squareup.otto.Produce;

/**
 * @author Artur Vasilov
 */
public class DataLoadedEvent {

    private DataLoadedEvent() {
    }

    @NonNull
    @Produce
    public static DataLoadedEvent produce() {
        return new DataLoadedEvent();
    }

}
