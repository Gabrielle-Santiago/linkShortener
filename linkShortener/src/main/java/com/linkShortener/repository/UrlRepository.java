package com.linkShortener.repository;

import com.linkShortener.model.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
