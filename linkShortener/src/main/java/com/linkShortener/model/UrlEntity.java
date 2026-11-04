package com.linkShortener.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collation = "urls")
public class UrlEntity {

    @Id
    private Long id;

    private String fullUrl;
    private String shortUrl;
    private Instant createAt;
    private  Instant expiresAt;

    public UrlEntity(){}

    public UrlEntity(String fullUrl, String shortUrl, Instant createAt, Instant expiresAt) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
    }
}
