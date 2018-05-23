package daewook.assignment.urlshortener.controller;

import daewook.assignment.urlshortener.interceptors.RequestHolder;
import daewook.assignment.urlshortener.v1.utils.UrlShortener;
import daewook.assignment.urlshortener.v2.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UrlShortenerController {
    private static String URL_TOKEN = "/";

    @Autowired
    private RequestHolder requestHolder;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Value("${url.shortener.version}")
    private Integer version;

    /**
     * Main Page
     * @return index.html
     */
    @RequestMapping
    public String index() {
        System.out.println(requestHolder.getFullHostname());
        return "index";
    }

    /**
     * Original URL에 대한 shortened 요청
     * @param originalUrl original URL
     * @param mnv
     * @return shortened URL
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView shorten(@RequestParam String originalUrl, ModelAndView mnv) {
        System.out.println(String.format("[Request] shorten URL. original URL = %s", originalUrl));
        mnv.setViewName("index");
        mnv.addObject("originalUrl", originalUrl);

        String shortenCode;
        if (version == 2) {
            shortenCode = urlShortenerService.toShortenUrl(originalUrl);
        } else {
            shortenCode = UrlShortener.shorten(originalUrl);
        }

        mnv.addObject("shortened", requestHolder.getFullHostname() + URL_TOKEN + shortenCode);
        System.out.println(String.format("[Response] shorten URL. shorten code = %s", shortenCode));
        return mnv;
    }

    /**
     * shortend URL을 전달 받아서 original URL로 리다이렉트
     * @param shortenCode
     * @return redirect:{original URL}
     */
    @RequestMapping(value = "/{shortenCode}", method = RequestMethod.GET)
    public String redirect(@PathVariable String shortenCode) {
        System.out.println(String.format("[Request] original URL. shorten code = %s", shortenCode));

        String originalUrl;
        if (version == 2) {
            originalUrl = urlShortenerService.toOriginalUrl(shortenCode);
        } else {
            originalUrl = UrlShortener.originate(shortenCode);
        }

        // shorten code에 해당하는 original url이 없는 경우에는 main 페이지로 이동
        if (originalUrl == null) {
            originalUrl = requestHolder.getFullHostname();
        }

        System.out.println(String.format("[Response] original URL. original URL = %s", originalUrl));
        return "redirect:" + originalUrl;
    }
}
