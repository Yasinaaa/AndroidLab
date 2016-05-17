package ru.itis.sqlbrite_homework.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.itis.sqlbrite_homework.data.model.Person;


public class Db {

   public static final String TABLE_NAME = "person";
   public static final String COLUMN_NAME = "name";

   public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        "UNIQUE (" + COLUMN_NAME + ")  ON CONFLICT REPLACE" +
                        " ); ";

   public static ContentValues toContentValues(Person person) {
       ContentValues values = new ContentValues();
       values.put(COLUMN_NAME, person.getName());
       return values;
   }

   public static Person parseCursor(Cursor cursor) {
            Person person = new Person();
            person.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            return person;
   }

    public static List<Person> parseCursorList(Cursor cursor) {
        List<Person> personList = new ArrayList<Person>();
        for(int i = 0; i < cursor.getCount(); i++){
            Person person = new Person();
            person.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            personList.add(person);
        }
        return personList;
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }
}
