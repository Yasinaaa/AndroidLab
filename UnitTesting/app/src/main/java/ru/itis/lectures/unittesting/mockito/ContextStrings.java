package ru.itis.lectures.unittesting.mockito;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.itis.lectures.unittesting.R;

/**
 * @author Artur Vasilov
 */
public class ContextStrings {

    @NonNull
    public static String getString(@NonNull Context context, @IntType int type) {
        @StringRes int stringId;
        switch (type) {
            case IntType.TYPE_1:
                stringId = R.string.string_1;
                break;

            case IntType.TYPE_2:
                stringId = R.string.string_2;
                break;

            case IntType.TYPE_3:
                stringId = R.string.string_3;
                break;

            default:
                throw new IllegalArgumentException("Type must be one of {1, 2, 3}");
        }

        return context.getResources().getString(stringId);
    }

}


