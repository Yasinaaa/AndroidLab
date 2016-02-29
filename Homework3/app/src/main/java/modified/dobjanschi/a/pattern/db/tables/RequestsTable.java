package modified.dobjanschi.a.pattern.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import modified.dobjanschi.a.pattern.activity.MainActivity;
import modified.dobjanschi.a.pattern.db.DatabaseUtils;
import modified.dobjanschi.a.pattern.db.RequestItemContract;
import modified.dobjanschi.a.pattern.db.SqliteHelper;
import modified.dobjanschi.a.pattern.service.model.RequestItem;

/**
 * @author Rustem
 */
public class RequestsTable {

    public static final Uri URI = SqliteHelper.BASE_CONTENT_URI
            .buildUpon()
            .appendPath(RequestItemContract.DatabaseRequests.TABLE_NAME)
            .build();

    private static final String[] COLUMNS_PROJECTION = new String[] {
            RequestItemContract.RequestColumns._ID,
            RequestItemContract.RequestColumns.REQUEST,
            RequestItemContract.RequestColumns.RESPONSE,
            RequestItemContract.RequestColumns.STATUS
    };
    public static void save(Context context, @NonNull RequestItem requestItem) {
        context.getContentResolver().insert(URI, toContentValues(requestItem));
    }

    public static void update(Context context, @NonNull RequestItem requestItem) {
        context.getContentResolver().update(URI, toContentValues(requestItem),
                RequestItemContract.RequestColumns.STATUS  + "=?", new String[]{requestItem.getStatus()});
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull RequestItem message) {
        ContentValues values = new ContentValues();
        values.put(RequestItemContract.RequestColumns.REQUEST, message.getRequest());
        values.put(RequestItemContract.RequestColumns.RESPONSE, message.getResponse());
        values.put(RequestItemContract.RequestColumns.STATUS, message.getStatus());
        return values;
    }

    @NonNull
    public static RequestItem fromCursor(@NonNull Cursor cursor) {
        String message = cursor.getString(cursor.getColumnIndex(RequestItemContract.RequestColumns.REQUEST));
        String status = cursor.getString(cursor.getColumnIndex(RequestItemContract.RequestColumns.STATUS));
        return new RequestItem(message, status);
    }



    @NonNull
    public static RequestItem getNewItem(Context context) {
        Cursor cursor = context.getContentResolver().query(URI,
                COLUMNS_PROJECTION,
                RequestItemContract.RequestColumns.STATUS + "=?",
                new String[]{MainActivity.STATUS_NEW},
                null);
        RequestItem requestItem;
        boolean stop = false;
        if (!cursor.moveToFirst()) {
            return null;
        }
        try {
            do {
                requestItem = fromCursor(cursor);
                if (requestItem.getStatus().equals("NEW")){
                    stop = true;
                    return requestItem;
                }
            } while (cursor.moveToNext() && stop);
            return null;
        } finally {
            DatabaseUtils.safeCloseCursor(cursor);
        }
    }

  /*  @NonNull
    public static List<RequestItem> listFromCursor(@NonNull Cursor cursor) {
        List<RequestItem> requestItems = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return requestItems;
        }
        try {
            do {
                requestItems.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return requestItems;
        } finally {
            DatabaseUtils.safeCloseCursor(cursor);
        }
    }
*/
    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }
}
