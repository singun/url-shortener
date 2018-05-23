package daewook.assignment.urlshortener.v1.utils;

import daewook.assignment.urlshortener.v1.key.RequestKey;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UrlShortenerTest {

    @Test
    public void testConverter() {
        RequestKey.init();
        assertThat(UrlShortener.shorten("https://en.wikipedia.org/wiki/URL_shortening"), is("a"));
        assertThat(UrlShortener.originate("a"), is("https://en.wikipedia.org/wiki/URL_shortening"));
    }
}
