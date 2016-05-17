package ru.itis.sqlbrite_homework.data.local;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.List;

import ru.itis.sqlbrite_homework.data.model.Person;
import rx.Observable;
import rx.Subscriber;
import rx.android.content.ContentObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DatabaseHelper {

    private String TAG = "DatabaseHelper";
    private SqlBrite mSqlBrite;

    public DatabaseHelper(Context context) {
        mSqlBrite = SqlBrite.create(new DbOpenHelper(context));
    }

    public SqlBrite getmSqlBrite() {
        return mSqlBrite;
    }

    public Observable<Person> getPeople() {
        return mSqlBrite.createQuery(Db.TABLE_NAME, "SELECT * FROM " + Db.TABLE_NAME)
                .flatMap(new Func1<Query, Observable<Cursor>>() {
                    @Override
                    public Observable<Cursor> call(Query query) {
                        return ContentObservable.fromCursor(query.run());
                    }
                }).map(new Func1<Cursor, Person>() {
                    @Override
                    public Person call(Cursor cursor) {
                        Log.d("SIZE", cursor.getCount() + "");
                        return Db.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<Person>> getPeopleList() {
        return mSqlBrite.createQuery(Db.TABLE_NAME, "SELECT * FROM " + Db.TABLE_NAME)
                .flatMap(new Func1<Query, Observable<Cursor>>() {
                    @Override
                    public Observable<Cursor> call(Query query) {
                        return ContentObservable.fromCursor(query.run());
                    }
                })/*.map(new Func1<Cursor, Person>() {
                    @Override
                    public Person call(Cursor cursor) {
                        Log.d("SIZE", cursor.getCount() + "");
                        return Db.parseCursor(cursor);
                    }
                })*/
                .map(new Func1<Cursor, List<Person>>() {
                    @Override
                    public List<Person> call(Cursor cursor) {
                        Log.d("SIZE", cursor.getCount() + "");
                        return  Db.parseCursorList(cursor);
                    }
                });
    }

    public Observable<Person> savePerson(final Person person) {

        return Observable.create(new Observable.OnSubscribe<Person>() {
            @Override
            public void call(Subscriber<? super Person> subscriber) {
                long result = mSqlBrite.insert(Db.TABLE_NAME, Db.toContentValues(person));
                Log.d(TAG,"result=" + result);
                if (result >= 0) subscriber.onNext(person);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Person> updatePerson(final Person person, final String newName) {

        return Observable.create(new Observable.OnSubscribe<Person>() {
            @Override
            public void call(Subscriber<? super Person> subscriber) {
                long result = mSqlBrite.update(Db.TABLE_NAME, Db.toContentValues(person), newName);
                Log.d(TAG,"result=" + result);
                if (result >= 0) subscriber.onNext(person);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Person> removePerson(final Person person) {

        return Observable.create(new Observable.OnSubscribe<Person>() {
            @Override
            public void call(Subscriber<? super Person> subscriber) {
                long result = mSqlBrite.delete(Db.TABLE_NAME, Db.getColumnName(), person.getName());
                Log.d(TAG,"result=" + result);
                if (result >= 0) subscriber.onNext(person);
                subscriber.onCompleted();
            }
        });
    }

}
