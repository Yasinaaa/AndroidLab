package ru.itis.lectures.unittesting.powermock;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class PersonTest {

    @Before
    public void setUp() throws Exception {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence value = (CharSequence) invocation.getArguments()[0];
                return value == null || value.length() == 0;
            }
        });
    }

    @Test
    public void testConstructor() throws Exception {
        Person person = new Person("Artur");
        assertNotNull(person);
    }

    @Test
    public void testName() throws Exception {
        Person person = new Person("Vasya");
        assertEquals("Vasya", person.getName());
    }
}

