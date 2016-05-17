package ru.itis.lectures.unittesting.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static ru.itis.lectures.unittesting.junit.MyCoolMath.multiply;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class MyCoolMathTest {

    @Test
    public void testZeroMultiply() throws Exception {
        assertEquals(0, multiply(0, 2031312));
        assertEquals(0, multiply(9371901, 0));
        assertEquals(0, multiply(3, 0));
        assertEquals(0, multiply(0, 6));
    }

    @Test
    public void testSmallNumbersMultiply() throws Exception {
        assertEquals(6, multiply(2, 3));
        assertEquals(8, multiply(4, 2));
        assertEquals(63, multiply(9, 7));
    }

    @Test
    public void testLargeNumbersMultiply() throws Exception {
        assertEquals(1830636, multiply(7596, 241));
        assertEquals(1011560232, multiply(1483, 682104));
    }
}


