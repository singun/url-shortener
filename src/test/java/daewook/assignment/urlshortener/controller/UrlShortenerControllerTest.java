package daewook.assignment.urlshortener.controller;

import daewook.assignment.urlshortener.configuration.Properties;
import daewook.assignment.urlshortener.interceptors.RequestHolder;
import daewook.assignment.urlshortener.v2.service.UrlShortenerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UrlShortenerControllerTest {
    @Mock
    private UrlShortenerService urlShortenerService;

    @Mock
    private RequestHolder requestHolder;

    @Mock
    private Properties properties;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(urlShortenerController)
                .build();
    }

    @Test
    public void testControllerIndexMethod() throws Exception {
        when(requestHolder.getFullHostname()).thenReturn("hostname");

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testControllerRootPostMethod() throws Exception {
        String protocol = "http";
        String host = "127.0.0.1";
        String shortened = "a";

        when(properties.getVersion()).thenReturn(2);
        when(requestHolder.getHost()).thenReturn(host);
        when(requestHolder.getProtocol()).thenReturn(protocol);
        when(requestHolder.getFullHostname()).thenReturn(protocol + "/" + host);

        when(urlShortenerService.toShortenUrl(anyString())).thenReturn(shortened);

        MockHttpServletRequestBuilder request = post("/").param("originalUrl", "test");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("shortened", requestHolder.getFullHostname()+ "/" + shortened));
    }

    @Test
    public void testControllerRedirectMethod() throws Exception {
        String shortenCode = "a";
        String originalUrl = "http://testurl";

        when(properties.getVersion()).thenReturn(2);
        when(urlShortenerService.toOriginalUrl(shortenCode)).thenReturn(originalUrl);

        MockHttpServletRequestBuilder request = get("/{shortenCode}", shortenCode);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getRedirectedUrl(), is(originalUrl));
    }
}
