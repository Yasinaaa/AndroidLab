package ru.itis.lectures.unittesting.mockito;

import android.support.annotation.IntDef;

/**
 * @author Artur Vasilov
 */
@IntDef({IntType.TYPE_1, IntType.TYPE_2, IntType.TYPE_3})
public @interface IntType {

    int TYPE_1 = 1;
    int TYPE_2 = 2;
    int TYPE_3 = 3;

}
