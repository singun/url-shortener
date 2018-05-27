package daewook.assignment.urlshortener.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    private RequestHolder requestHolder;

    /**
     * HttpServletRequest로 부터 request 정보 수집하여 requestHolder에 저장
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            requestHolder.setHost(request.getHeader("host"));
            requestHolder.setProtocol(request.getScheme());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
