package modified.dobjanschi.a.pattern.db;

import android.provider.BaseColumns;

import modified.dobjanschi.a.pattern.service.model.RequestItem;

/**
 * @author Artur Vasilov
 */
public class RequestItemContract {

    public interface RequestColumns extends BaseColumns {
        String REQUEST = "request";
        String RESPONSE = "response";
        String STATUS = "status";
    }

    public interface DatabaseRequests {

        String TABLE_NAME = RequestItem.class.getSimpleName();

        String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + RequestColumns._ID + " integer primary key autoincrement, "
                + RequestColumns.REQUEST + " TEXT, "
                + RequestColumns.RESPONSE + " TEXT,"
                + RequestColumns.STATUS + " TEXT"
                + ");";

        String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
