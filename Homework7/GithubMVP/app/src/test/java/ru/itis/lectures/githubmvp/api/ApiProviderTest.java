package ru.itis.lectures.githubmvp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ApiProviderTest {

    @Test
    public void testSetAndGetProvider() throws Exception {
        ApiProvider provider = new DefaultApiProvider("token");
        ApiFactory.setProvider(provider);

        assertTrue(provider == ApiFactory.getProvider());
    }
}
