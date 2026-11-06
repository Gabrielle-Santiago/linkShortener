package com.linkShortener.service;

import com.linkShortener.model.UrlEntity;
import com.linkShortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class UrlService {

    private UrlRepository repository;
    private String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private int code_length = 7;
    private SecureRandom random = new SecureRandom();

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String generateShortUrl(){
        StringBuilder shortUrl = new StringBuilder(code_length);

        for (int i = 0; i < code_length; i++) {
            int index = random.nextInt(characters.length());
            shortUrl.append(characters.charAt(index));
        }

        return shortUrl.toString();
    }

    public String shortenUrl(String fullUrl) {
        String shortUrl = generateShortUrl();

        UrlEntity entity = new UrlEntity();
        entity.setFullUrl(fullUrl);
        entity.setShortUrl(shortUrl);

        Instant instant = Instant.now();
        entity.setCreateAt(instant.now());
        entity.setExpiresAt(instant.now().plus(30, ChronoUnit.DAYS));
        
        repository.save(entity);

        return shortUrl;
    }
}
