package com.linkShortener.service;

import com.linkShortener.component.LinkValidations;
import com.linkShortener.model.UrlEntity;
import com.linkShortener.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    UrlService service;

    @Mock
    UrlRepository repository;

    @Mock
    LinkValidations validations;

    @Test
    void generateShortUrl() {
        String shortUrl = service.generateShortUrl();

        assertEquals(7, shortUrl.length());
        assertTrue(shortUrl.matches("[A-Za-z0-9]{7}"));
    }

    @Test
    void shortenUrl() {
        String fullUrl = "https://google.com";

        when(validations.checkExistingLinks(fullUrl)).thenReturn(null);
        when(validations.checkExistingUrl(fullUrl)).thenReturn(null);
        doNothing().when(validations).checkUrlValidFormat(fullUrl);

        String result = service.shortenUrl(fullUrl);

        assertNotNull(result);
        assertTrue(result.matches("[A-Za-z0-9]{7}"));

        ArgumentCaptor<UrlEntity> captor = ArgumentCaptor.forClass(UrlEntity.class);

        verify(repository).save(captor.capture());

        UrlEntity savedEntity = captor.getValue();

        assertEquals(fullUrl, savedEntity.getFullUrl());
        assertEquals(result, savedEntity.getShortUrl());
        assertNotNull(savedEntity.getCreateAt());
        assertNotNull(savedEntity.getExpiresAt());

        verify(validations).checkExistingLinks(fullUrl);
        verify(validations).checkExistingUrl(fullUrl);
        verify(validations).checkUrlValidFormat(fullUrl);
    }

    @Test
    void getFullUrl() {
        String shortUrl = "fIqG56r";

        UrlEntity entity = new UrlEntity(
                "https://google.com",
                shortUrl,
                Instant.now(),
                Instant.now().plus(30, ChronoUnit.DAYS)
        );

        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(entity));

        Optional<UrlEntity> result = service.getFullUrl(shortUrl);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());

        verify(repository).findByShortUrl(shortUrl);
    }

    @Test
    void getFullUrlNotFound() {
        String shortUrl = "fIqG56r";

        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getFullUrl(shortUrl));

        verify(repository).findByShortUrl(shortUrl);
    }
}