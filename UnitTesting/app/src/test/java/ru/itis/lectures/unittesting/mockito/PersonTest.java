package ru.itis.lectures.unittesting.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class PersonTest {

    @Test
    public void testToString() throws Exception {
        Person person = new Person();
        person.setName("Vasya");
        String result = "Person{name='Vasya' age='42'}";
        //this won't actually work if we run this under jvm (without Android environment)
        //assertEquals(result, person.toString());
    }

    @Test
    public void testToStringMocked() throws Exception {
        Person person = mock(Person.class);
        doCallRealMethod().when(person).setName(anyString());
        when(person.toString()).thenCallRealMethod();

        person.setName("Vasya");
        when(person.getAge()).thenReturn(42);

        String result = "Person{name='Vasya' age='42'}";
        assertEquals(result, person.toString());
    }
}

