package com.linkShortener.service;

import com.linkShortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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
}
