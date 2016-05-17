package ru.itis.lectures.unittesting.powermock;

import android.os.Looper;
import android.support.annotation.NonNull;

import com.squareup.otto.Bus;

/**
 * @author Artur Vasilov
 */
public class OttoBus {

    private final Bus mBus;

    private OttoBus(Bus bus) {
        mBus = bus;
    }

    @NonNull
    public static OttoBus get() {
        return Holder.INSTANCE;
    }

    public final void post(final Object event) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            MainQueue.get().invoke(new Runnable() {
                @Override
                public void run() {
                    mBus.post(event);
                }
            });
        } else {
            mBus.post(event);
        }
    }

    public final void register(@NonNull Object object) {
        mBus.register(object);
    }

    public final void unregister(@NonNull Object object) {
        mBus.unregister(object);
    }

    private static final class Holder {
        public static final OttoBus INSTANCE = new OttoBus(new Bus());
    }

}
