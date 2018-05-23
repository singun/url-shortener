package daewook.assignment.urlshortener.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * Exception 발생 시 error 페이지로 리다이렉트
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handle(Exception e) {
        e.printStackTrace();
        return "error";
    }
}
