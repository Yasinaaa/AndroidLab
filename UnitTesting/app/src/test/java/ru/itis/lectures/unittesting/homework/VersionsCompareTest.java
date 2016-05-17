package ru.itis.lectures.unittesting.homework;

import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class VersionsCompareTest {

    @Test
    public void testWrongNumbersFormat() throws Exception {
        try {
            VersionsCompare.compare("1.4g", "1.7");
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    private void setUp(String text) throws Exception{

        when(TextUtils.split(text,"\\.")).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                String[] numbers1 = a.toString().split("\\.");
                return numbers1;
            }
        });
    }


    private void callTest(int answer, String num1, String num2) throws Exception{
        setUp(num1);
        setUp(num2);
        assertEquals(answer, VersionsCompare.compare(num1, num2));
    }

    @Test
    public void test2() throws Exception {
        mockStatic(TextUtils.class);

        callTest(1, "6.9", "4.9");
        callTest(-1, "4.9", "5.9");

        callTest(1, "4.9", "4.7");
        callTest(-1, "4.3", "4.9");
        callTest(0, "4.9", "4.9");

    }

}
