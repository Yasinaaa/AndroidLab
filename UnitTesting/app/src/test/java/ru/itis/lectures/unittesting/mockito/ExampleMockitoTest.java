package ru.itis.lectures.unittesting.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ExampleMockitoTest {

    @Test
    public void testMockitoSuffix() throws Exception {
        List values = mock(List.class);
        when(values.get(0)).thenReturn(10);
        when(values.indexOf(10)).thenReturn(0);
        when(values.contains(any())).thenThrow(new RuntimeException());
        when(values.iterator()).thenAnswer(new Answer<Iterator>() {
            @Override
            public Iterator answer(InvocationOnMock invocation) throws Throwable {
                Iterator iterator = mock(Iterator.class);
                when(iterator.hasNext()).thenReturn(false);
                return iterator;
            }
        });
    }

    @Test
    public void testMockitoPrefix() throws Exception {
        List values = mock(List.class);
        doReturn(10).when(values).get(0);
        doReturn(0).when(values).indexOf(10);
        doThrow(new RuntimeException()).when(values).contains(any());
        doAnswer(new Answer<Iterator>() {
            @Override
            public Iterator answer(InvocationOnMock invocation) throws Throwable {
                Iterator iterator = mock(Iterator.class);
                when(iterator.hasNext()).thenReturn(false);
                return iterator;
            }
        }).when(values).iterator();

        doNothing().when(values).clear();
    }
}


