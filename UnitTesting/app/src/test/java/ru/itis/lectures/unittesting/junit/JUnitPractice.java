package ru.itis.lectures.unittesting.junit;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class JUnitPractice {

    @Test
    public void testAzaza() throws Exception {
        try {
            LocalSecurePassword.destroy();
            LocalSecurePassword.getInstance();
            fail();
        } catch (IllegalStateException ignored) {
        }
    }

    @Test
    public void testGetInstanceNotNull() throws Exception {
        LocalSecurePassword.create("aaa");
        assertNotNull(LocalSecurePassword.getInstance());
    }

    @Test
    public void testExceptionInstance() throws Exception {
        try {
            LocalSecurePassword.getInstance();
            fail();
        } catch (IllegalStateException ignored) {
        }

    }

    @Test
    public void testCreatedInstance() throws Exception {
        LocalSecurePassword securePassword = LocalSecurePassword.create("password");
        assertNotNull(securePassword);
    }

    @Test
    public void testCorrectPassword() throws Exception {
        LocalSecurePassword.create("password");
        assertTrue(LocalSecurePassword.getInstance().checkPassword("password"));
    }

    @Test
    public void testIncorrectPassword() throws Exception {
        LocalSecurePassword.create("password");
        assertFalse(LocalSecurePassword.getInstance().checkPassword("password1"));
    }
}
