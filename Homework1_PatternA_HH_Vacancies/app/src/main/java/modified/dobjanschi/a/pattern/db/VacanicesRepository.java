package modified.dobjanschi.a.pattern.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import modified.dobjanschi.a.pattern.model.Vacancy;

/**
 * Created by yasina on 14.02.16.
 */
public class VacanicesRepository {

    public static final String TAG = "VacanicesRepository";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;
    private String[] mAllColumns = {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_EMPLOYER_NAME,
    DataBaseHelper.COLUMN_REQUIREMENT,DataBaseHelper.COLUMN_RESPONSIBILITY};

    public VacanicesRepository(Context context) {
        mDbHelper = new DataBaseHelper(context);
        this.mContext = context;
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void add(Vacancy.Item mVacancy) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_NAME, mVacancy.name);
        values.put(DataBaseHelper.COLUMN_EMPLOYER_NAME, mVacancy.employer.name);
        values.put(DataBaseHelper.COLUMN_REQUIREMENT, mVacancy.snippet.requirement);
        values.put(DataBaseHelper.COLUMN_RESPONSIBILITY, mVacancy.snippet.responsibility);

        long insertId = mDatabase.insertWithOnConflict(DataBaseHelper.TABLE_VACANCIES, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);

    }

    public void dropTable(){
        try {
            mDatabase.execSQL("DROP TABLE IF EXISTS " + DataBaseHelper.TABLE_VACANCIES);
            mDatabase.execSQL(DataBaseHelper.SQL_CREATE_TABLE_VACANCIES);
        }catch (java.lang.NullPointerException e){
            
        }
    }

    public void delete(Vacancy.Item mVacancy) {
        long id = mVacancy.id;
        mDatabase.delete(DataBaseHelper.TABLE_VACANCIES, DataBaseHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Vacancy.Item> getAllVacancies() {
        List<Vacancy.Item> allItems = new ArrayList<Vacancy.Item>();

        mDatabase = mDbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_VACANCIES,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vacancy.Item item = cursorToVacancy(cursor);
            allItems.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return allItems;
    }


    public Vacancy.Item getVacancyById(long id) {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_VACANCIES,mAllColumns,
                DataBaseHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Vacancy.Item item = cursorToVacancy(cursor);
        return item;
    }

    private Vacancy.Item cursorToVacancy(Cursor cursor) {
        Vacancy.Item mVacancy = new Vacancy.Item();
        mVacancy.id = cursor.getLong(0);
        mVacancy.name = cursor.getString(1);
        mVacancy.employer = new Vacancy.Item.Employer();
        mVacancy.employer.name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMPLOYER_NAME));
        mVacancy.snippet = new Vacancy.Item.Snippet();
        mVacancy.snippet.requirement = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_REQUIREMENT));
        mVacancy.snippet.responsibility = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_RESPONSIBILITY));
        return mVacancy;
    }

}


