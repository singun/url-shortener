package daewook.assignment.urlshortener.v2.key;

public class OriginalUrlKey {
    private static final String KEY_PREFIX = "original:";

    /**
     * REDIS 저장용 키를 생성한다.
     * @param originalUrl
     * @return "original:{originalUrl}"
     */
    public static String gen(String originalUrl) {
        return KEY_PREFIX + originalUrl;
    }
}
