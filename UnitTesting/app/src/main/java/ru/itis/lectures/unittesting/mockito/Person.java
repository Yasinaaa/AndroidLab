package ru.itis.lectures.unittesting.mockito;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class Person {

    private String mName;

    public Person() {
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public int getAge() {
        /*
        Cursor cursor = getContext().getContentResolver().query(getUri(), null,
                "name=", new String[] {mName}, null);
        return cursor.getInt(0);
        */
        return 42;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + mName + '\'' +
                " age='" + getAge() + '\'' +
                '}';
    }
}


