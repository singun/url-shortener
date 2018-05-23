package daewook.assignment.urlshortener.v2.service;

import daewook.assignment.urlshortener.utils.Base52Encoder;
import daewook.assignment.urlshortener.v2.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    /**
     * original URL을 shorten code로 변환한다.
     * @param originalUrl
     * @return shorten code
     */
    public String toShortenUrl(String originalUrl) {
        long alreadyRequested = urlRepository.getShortenNumber(originalUrl);
        if (alreadyRequested > 0) {
            return Base52Encoder.encode(alreadyRequested);
        }

        long shortenNumber = urlRepository.init();
        urlRepository.setOriginalUrlAndShortenNumber(originalUrl, shortenNumber);
        urlRepository.setShortenNumberAndOriginalUrl(shortenNumber, originalUrl);
        return Base52Encoder.encode(shortenNumber);
    }

    /**
     * shorten code를 original URL로 변환한다.
     * @param shortenCode
     * @return original URL을 반환한다.
     *         shorten code에 해당하는 값이 없는 경우, null을 반환한다.
     */
    public String toOriginalUrl(String shortenCode) {
        long shortenNumber = Base52Encoder.decode(shortenCode);
        return urlRepository.getOriginalUrl(shortenNumber);
    }
}
