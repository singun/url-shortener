package daewook.assignment.urlshortener.v1.key;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestKeyTest {

    private static final String TEST_URL = "https://en.wikipedia.org/wiki/URL_shortening";

    @Test
    public void testGen() {
        RequestKey.init();
        assertEquals(RequestKey.gen(), 0l);
    }

    @Test
    public void testGen2() {
        RequestKey.init();
        assertEquals(RequestKey.gen(TEST_URL), 0l);
        assertEquals(RequestKey.getKeyBy(TEST_URL), Long.valueOf(0l));
        assertEquals(RequestKey.getOriginalUrlBy(0l), TEST_URL);
    }
}
