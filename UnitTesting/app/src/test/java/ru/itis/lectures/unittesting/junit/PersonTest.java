package ru.itis.lectures.unittesting.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class PersonTest {

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

