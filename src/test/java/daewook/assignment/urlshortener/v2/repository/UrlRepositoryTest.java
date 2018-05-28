package daewook.assignment.urlshortener.v2.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class UrlRepositoryTest {

    private static final String ORIGINAL_URL = "http://test.url";
    private static final long SHORTEN_NUMBER = -2l;

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void testInit() {
        long requestCount = urlRepository.getRequestCount();
        assertEquals(urlRepository.init(), requestCount+1);
    }

    @Test
    public void testSetOriginalUrlAndUrlKey() {
//        assertTrue(urlRepository.destroy(OriginalUrlKey.gen(ORIGINAL_URL)));
        assertTrue(urlRepository.setOriginalUrlAndShortenNumber(ORIGINAL_URL, SHORTEN_NUMBER));
        assertThat(urlRepository.getShortenNumber(ORIGINAL_URL), is(SHORTEN_NUMBER));
    }

    @Test
    public void testSetUrlKeyAndOriginalUrl() {
//        assertTrue(urlRepository.destroy(ShortenCodeKey.gen(SHORTEN_NUMBER)));
        assertTrue(urlRepository.setShortenNumberAndOriginalUrl(SHORTEN_NUMBER, ORIGINAL_URL));
        assertThat(urlRepository.getOriginalUrl(SHORTEN_NUMBER), is(ORIGINAL_URL));
    }

    @Test
    public void testNil() {
        assertNull(urlRepository.getOriginalUrl(-1l));
        assertEquals(urlRepository.getShortenNumber(""), -1l);
    }

}
