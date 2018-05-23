package daewook.assignment.urlshortener.v1.utils;

import daewook.assignment.urlshortener.v1.key.RequestKey;
import daewook.assignment.urlshortener.utils.Base52Encoder;

public class UrlShortener {
    /**
     * 원래 URL로 부터 단축 CODE를 만든다.
     * @param originalUrl 원래 URL
     * @return 단축 CODE
     */
    public static String shorten(String originalUrl) {
        return Base52Encoder.encode(RequestKey.gen(originalUrl));
    }

    /**
     * 단축 URL로 부터 원래 URL을 구한다
     * @param shortenUrl 단축 URL
     * @return 원래 URL
     */
    public static String originate(String shortenUrl) {
        return RequestKey.getOriginalUrlBy(Base52Encoder.decode(shortenUrl));
    }
}
