package com.linkShortener.component;

import com.linkShortener.model.UrlEntity;
import com.linkShortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

@Component
public class LinkValidations {

    private UrlRepository repository;

    public LinkValidations(UrlRepository repository) {
        this.repository = repository;
    }

    public String checkExistingLinks(String fullUrl) {
        return repository.findByFullUrl(fullUrl)
                .map(UrlEntity::getShortUrl)
                .orElse(null);
    }

    public String checkExistingShortLinks(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .map(UrlEntity::getShortUrl)
                .orElse(null);
    }
}
