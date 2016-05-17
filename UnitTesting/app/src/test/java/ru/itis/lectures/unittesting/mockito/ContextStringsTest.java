package ru.itis.lectures.unittesting.mockito;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.itis.lectures.unittesting.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilvo
 */
@RunWith(JUnit4.class)
public class ContextStringsTest {

    @Test
    public void testString1() throws Exception {
        Context context = getContext("String 1", R.string.string_1);
        String result = ContextStrings.getString(context, IntType.TYPE_1);
        assertEquals("String 1", result);
    }

    @Test
    public void testString2() throws Exception {
        Context context = getContext("String 2", R.string.string_2);
        String result = ContextStrings.getString(context, IntType.TYPE_2);
        assertEquals("String 2", result);
    }

    @Test
    public void testString3() throws Exception {
        Context context = getContext("String 3", R.string.string_3);
        String result = ContextStrings.getString(context, IntType.TYPE_3);
        assertEquals("String 3", result);
    }

    @Test
    public void testFail() throws Exception {
        try {
            Context context = getContext("Aaaaa", R.string.string_3);
            //noinspection ResourceType
            ContextStrings.getString(context, 10);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @NonNull
    private Context getContext(@NonNull String result, @StringRes int mockedResource) {
        Context context = mock(Context.class);
        Resources resources = mock(Resources.class);
        when(resources.getString(mockedResource)).thenReturn(result);
        when(context.getResources()).thenReturn(resources);
        return context;
    }
}
