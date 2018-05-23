package daewook.assignment.urlshortener.v1.key;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RequestKey {
    /**
     * shorten 요청 한건 당, unique한 key를 부여받는다.
     */
    private static AtomicLong key = new AtomicLong(-1l);

    /**
     * 동일한 original URL의 shorten 요청에 대하여
     * 동일한 shorten URL 반환을 위해서 caching 한다.
     * { key : original URL, value : atomic key}
     */
    private static Map<String, Long> originalUrlByKeyMap = new HashMap<>();

    /**
     * atomic key caching
     *  { key : atomic key, value : original URL}
     */
    private static Map<Long, String> keyByOriginalUrlMap = new HashMap<>();

    /**
     * redisTemplate 테스트 용으로 사용
     */
    @Deprecated
    public static void init() {
        key = new AtomicLong(-1l);
    }

    /**
     * Unique key를 생성한다.
     * @return unique key
     */
    public static long gen() {
        return key.incrementAndGet();
    }

    /**
     * Original URL에 매핑된 shorten number를 반환한다.
     * 동일한 URL에 대한 요청이 이미 처리되었다면 캐시하고 있는 값을 반환한다.
     * 그렇지 않을 경우, 새로 값을 채번한 후 각각 캐시한 후에 shorten number를 반환한다.
     * @param originalUrl
     * @return shorten number
     */
    public static long gen(String originalUrl) {
        Long alreadyRequested = getKeyBy(originalUrl);
        if (alreadyRequested != null) {
            return alreadyRequested;
        }

        Long key = gen();
        setOriginalUrlAndKey(originalUrl, key);
        setKeyAndOriginalUrl(key, originalUrl);
        return key;
    }

    /**
     * { key : original URL, value : shorten number} 의 형태로 저장한다.
     * @param originalUrl
     * @param key shorten number
     * @return originalURl에 대해서 이전에 저장된 shorten number을 반환한다.
     *         original URL이 이전에 없는 경우 null을 반환한다.
     */
    private static Long setOriginalUrlAndKey(String originalUrl, Long key) {
        return originalUrlByKeyMap.put(originalUrl, key);
    }

    /**
     * { key : original URL, value : shorten number} 의 형태로 저장한다.
     * @param key shorten number
     * @param originalUrl
     * @return shorten number에 대해서 이전에 저장된 original URL을 반환한다.
     *         shorten number가 이전에 없는 경우 null을 반환한다.
     */
    private static String setKeyAndOriginalUrl(Long key, String originalUrl) {
        return keyByOriginalUrlMap.put(key, originalUrl);
    }

    /**
     * original URL에 해당하는 shorten number를 반환한다.
     * @param originalUrl
     * @return shorten number
     */
    public static Long getKeyBy(String originalUrl) {
        return originalUrlByKeyMap.get(originalUrl);
    }

    /**
     * shorten key에 해당하는 original URL을 반환한다.
     * @param key
     * @return original URL
     */
    public static String getOriginalUrlBy(Long key) {
        return keyByOriginalUrlMap.get(key);
    }
}
