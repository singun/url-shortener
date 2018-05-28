package daewook.assignment.urlshortener.controller;

import daewook.assignment.urlshortener.common.DefaultExceptionHandler;
import daewook.assignment.urlshortener.interceptors.RequestInterceptor;
import daewook.assignment.urlshortener.v2.service.UrlShortenerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerControllerTest {

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlShortenerController)
                .addInterceptors(new RequestInterceptor())
                .setControllerAdvice(new DefaultExceptionHandler())
                .build();
    }

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Ignore
    @Test
    public void test() throws Exception {
        given(urlShortenerService.toShortenUrl("http://test.url")).willReturn("abcd");

//        urlShortenerService.toShortenUrl("http://test.url");

        mockMvc.perform(post("/").param("originalUrl", "http://test.url"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("shortened", "abcd"));
    }
}
