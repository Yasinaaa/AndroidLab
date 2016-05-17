package ru.itis.lectures.unittesting.powermock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(OttoBus.class)
public class PowerMockPractice {

    @Before
    public void setUp() throws Exception {
        mockStatic(OttoBus.class);
        PowerMockito.when(OttoBus.get()).thenAnswer(new Answer<OttoBus>() {
            @Override
            public OttoBus answer(InvocationOnMock invocation) throws Throwable {
                return PowerMockito.mock(OttoBus.class);
            }
        });
    }

    @Test
    public void testDataLoadedSuccessful() throws Exception {
        String data = "caacsa. response=;xsa";
        assertEquals(DataLoadedProcessor.SUCCESS, DataLoadedProcessor.processDataLoaded(data));
    }

    @Test
    public void testDataLoadedError() throws Exception {
        String data = "sxaa";
        assertEquals(DataLoadedProcessor.ERROR, DataLoadedProcessor.processDataLoaded(data));
    }
}
