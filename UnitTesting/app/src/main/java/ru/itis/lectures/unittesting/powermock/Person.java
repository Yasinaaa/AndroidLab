package ru.itis.lectures.unittesting.powermock;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Artur Vasilov
 */
public class Person {

    private final String mName;

    public Person(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getName() {
        return TextUtils.isEmpty(mName) ? "empty_name" : mName;
    }
}


