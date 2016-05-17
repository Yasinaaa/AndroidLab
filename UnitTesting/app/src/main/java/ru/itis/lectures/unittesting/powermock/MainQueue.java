package ru.itis.lectures.unittesting.powermock;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Artur Vasilov
 */
public class MainQueue {

    private static volatile Handler sMainHandler;

    private MainQueue() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    @NonNull
    public static MainQueue get() {
        return Holder.MAIN_QUEUE;
    }

    @NonNull
    public static Handler getMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }

    public void invoke(Runnable runnable) {
        sMainHandler.post(runnable);
    }

    private static class Holder {
        private static final MainQueue MAIN_QUEUE = new MainQueue();
    }
}
