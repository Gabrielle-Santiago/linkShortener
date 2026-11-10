package com.linkShortener.component;

import com.linkShortener.model.UrlEntity;
import com.linkShortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

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

    public String checkExistingUrl(String fullUrl) {
        return repository.findByFullUrl(fullUrl)
                .map(url -> checkExistingShortLinks(url.getShortUrl()))
                .orElse(null);
    }

    public void checkUrlValidFormat(String fullUrl) {
        if (fullUrl == null || fullUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        try {
            URI uri = new URI(fullUrl);

            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
                throw new IllegalArgumentException("Invalid URL protocol");
            }

            if (host == null || host.isBlank()) {
                throw new IllegalArgumentException("Invalid URL host");
            }

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }
}
