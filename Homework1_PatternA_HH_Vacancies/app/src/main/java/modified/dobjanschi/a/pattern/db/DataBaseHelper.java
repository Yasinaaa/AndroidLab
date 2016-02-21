package modified.dobjanschi.a.pattern.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yasina on 14.02.16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DataBaseHelper";

    public static final String TABLE_VACANCIES = "vacancy";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMPLOYER_NAME = "employer_name";
    public static final String COLUMN_REQUIREMENT = "requirement";
    public static final String COLUMN_RESPONSIBILITY = "responsibility";
    private static final String DATABASE_NAME = "vacancies.db";
    private static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_TABLE_VACANCIES = "CREATE TABLE " + TABLE_VACANCIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_EMPLOYER_NAME + " TEXT,"
            + COLUMN_REQUIREMENT + " TEXT, "
            + COLUMN_RESPONSIBILITY + " TEXT" +
            ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_VACANCIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACANCIES);
        onCreate(db);
    }

}

