package ru.itis.lectures.unittesting.mockito;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.content.pm.PackageManager.PERMISSION_DENIED;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class MockitoPractice {

    @Test
    public void testContextMocked() throws Exception {
        Context context = mock(Context.class);
        when(context.checkPermission(anyString(), anyInt(), anyInt()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) throws Throwable {
                        String permission = (String) invocation.getArguments()[0];
                        if (permission.equals(Manifest.permission.READ_CONTACTS)) {
                            return PackageManager.PERMISSION_DENIED;
                        }
                        return PackageManager.PERMISSION_GRANTED;
                    }
                });

        boolean result = PermissionUtils.checkPermission(context,
                Manifest.permission.READ_CONTACTS);
        assertFalse(result);

        result = PermissionUtils.checkPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        assertTrue(result);
    }
}
