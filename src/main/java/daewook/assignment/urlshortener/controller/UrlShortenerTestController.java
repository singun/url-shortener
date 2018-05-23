package daewook.assignment.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Deprecated
@Controller
@RequestMapping("/test")
public class UrlShortenerTestController {

    /**
     * redirect 동작 확인을 위한 테스트 api
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/JZfOQNro", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:https://en.wikipedia.org/wiki/URL_shortening";
    }

    /**
     * form 요청 처리 및 결과 확인을 위한 테스트 api
     * @param originalUrl
     * @param mnv
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView compact(@RequestParam String originalUrl, ModelAndView mnv) {
        mnv.setViewName("index");
        mnv.addObject("originalUrl", originalUrl);
        mnv.addObject("shortenUrl", "http://localhost:8080/test/JZfOQNro");
        return mnv;
    }
}
