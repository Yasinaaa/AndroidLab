package ru.itis.lectures.unittesting.mockito;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermission(Context context, @NonNull String permission) {
        //return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        return context.checkPermission(permission, 0, 0) == PackageManager.PERMISSION_GRANTED;
    }
}


