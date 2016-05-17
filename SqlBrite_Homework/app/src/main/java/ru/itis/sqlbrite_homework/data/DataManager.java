package ru.itis.sqlbrite_homework.data;

import android.content.Context;
import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.model.Person;
import rx.Observable;
import rx.Scheduler;

public class DataManager {

    private DatabaseHelper mDatabaseHelper;

    public DataManager(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public Observable<Person> savePerson(Person person) {
        return mDatabaseHelper.savePerson(person);
    }

    public Observable<Person> getPeople() {
        return mDatabaseHelper.getPeople();
    }

    public Observable<Person> removePerson(Person person){
        return mDatabaseHelper.removePerson(person);
    }

    public Observable<Person> updatePerson(Person person, String newName){
        return mDatabaseHelper.updatePerson(person, newName);
    }
}
