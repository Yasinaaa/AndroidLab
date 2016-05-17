package ru.itis.lectures.unittesting.tdd;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class Person {

    private String mName;

    public Person(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}



