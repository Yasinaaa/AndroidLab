package ru.itis.lectures.unittesting.junit;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class LocalSecurePassword {

    private static LocalSecurePassword sInstance;

    private int mHash = 0;

    private LocalSecurePassword(@NonNull String password) {
        mHash = password.hashCode();
    }

    @NonNull
    public static LocalSecurePassword getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("You should initialize instance fist");
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
    }

    @NonNull
    public static LocalSecurePassword create(@NonNull String password) {
        sInstance = new LocalSecurePassword(password);
        return sInstance;
    }

    public boolean checkPassword(@NonNull String password) {
        return mHash == password.hashCode();
    }
}


