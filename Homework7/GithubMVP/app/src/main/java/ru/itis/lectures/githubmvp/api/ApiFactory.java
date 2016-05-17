package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static ApiProvider sProvider;

    private ApiFactory() {
    }

    public static void setProvider(@NonNull ApiProvider provider) {
        sProvider = provider;
    }

    @NonNull
    public static ApiProvider getProvider() {
        return sProvider;
    }

}
