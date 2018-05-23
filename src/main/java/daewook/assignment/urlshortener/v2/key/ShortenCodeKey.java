package daewook.assignment.urlshortener.v2.key;

public class ShortenCodeKey {
    private static final String KEY_PREFIX = "shorten:";

    /**
     * REDIS 저장용 키를 생성한다.
     * @param shortenNumber
     * @return "shorten::{shortenNumber}"
     */
    public static String gen(long shortenNumber) {
        return KEY_PREFIX + String.valueOf(shortenNumber);
    }
}
