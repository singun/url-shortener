package daewook.assignment.urlshortener.v2.key;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShortenCodeKeyTest {
    @Test
    public void testShortenCodeKeyGenTest() {
        assertThat(ShortenCodeKey.gen(1l), is("shorten:1"));
    }
}
