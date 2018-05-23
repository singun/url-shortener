package daewook.assignment.urlshortener.v2.key;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OriginalUrlKeyTest {

    @Test
    public void testGen() {
        assertThat(OriginalUrlKey.gen("https://en.wikipedia.org/wiki/URL_shortening"), is("original:https://en.wikipedia.org/wiki/URL_shortening"));
    }
}
