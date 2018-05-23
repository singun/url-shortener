package daewook.assignment.urlshortener.v2.repository;

import daewook.assignment.urlshortener.v2.key.OriginalUrlKey;
import daewook.assignment.urlshortener.v2.key.ShortenCodeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlRepository {
    /**
     * URL_SHORTENER application의 변환 요청 key
     */
    private static final String REQUEST_COUNT = "shorten:url:request:count";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Deprecated
    public boolean destroy(String key) {
        return redisTemplate.hasKey(key) ? redisTemplate.delete(key) : true;
    }

    @Deprecated
    public long getRequestCount() {
        return redisTemplate.hasKey(REQUEST_COUNT) ? Long.valueOf(redisTemplate.opsForValue().get(REQUEST_COUNT)) : 0;
    }

    /**
     * shorten:url:request:count 에 대해서 value를 하나씩 증가시켜 shorten number 로 사용할 값을 얻어온다.
     * @return shorten number
     */
    public long init() {
        return redisTemplate.opsForValue().increment(REQUEST_COUNT, 1l);
    }

    /**
     * { key : original URL, value : shorten number}
     * @param originalUrl
     * @param shortenNumber
     * @return 성공 여부
     */
    public boolean setOriginalUrlAndShortenNumber(String originalUrl, Long shortenNumber) {
        return redisTemplate.opsForValue().setIfAbsent(OriginalUrlKey.gen(originalUrl), shortenNumber.toString());
    }

    /**
     * { key : shorten number, value : original URL}
     * @param shortenNumber
     * @param originalUrl
     * @return
     */
    public boolean setShortenNumberAndOriginalUrl(long shortenNumber, String originalUrl) {
        return redisTemplate.opsForValue().setIfAbsent(ShortenCodeKey.gen(shortenNumber), originalUrl);
    }

    /**
     * shorten number 를 이용해서 original URL을 꺼내온다.
     * @param shortenNumber
     * @return original URL
     */
    public String getOriginalUrl(long shortenNumber) {
        return redisTemplate.opsForValue().get(ShortenCodeKey.gen(shortenNumber));
    }

    /**
     * original URL 을 이용해서 shorten number를 꺼내온다.
     * @param originalUrl
     * @return shorten number. original URL에 대한 이전 요청이 없는 경우 -1을 반환한다.
     */
    public long getShortenNumber(String originalUrl) {
        String value = redisTemplate.opsForValue().get(OriginalUrlKey.gen(originalUrl));
        if (value == null) {
            return -1l;
        }

        return Long.parseLong(value);
    }
}
