package ru.itis.lectures.unittesting.tdd;

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
    public void testConstructors() throws Exception {
        Person person = new Person("Vasya");
        assertNotNull(person);
    }

    @Test
    public void testGetName() throws Exception {
        Person person = new Person("Vasya");
        assertEquals("Vasya", person.getName());
    }

    @Test
    public void testGetName2() throws Exception {
        Person person = new Person("Dima");
        assertEquals("Dima", person.getName());
    }
}


