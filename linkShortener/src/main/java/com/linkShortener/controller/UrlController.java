package com.linkShortener.controller;

import com.linkShortener.model.dto.UrlRequest;
import com.linkShortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/url")
public class UrlController {

    private UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequest request) {
        String url = service.shortenUrl(request.fullUrl());
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        var url = service.getFullUrl(shortUrl);

        if (url.isPresent()) {
            URI originalUrl = URI.create(url.get().getFullUrl());

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(originalUrl)
                    .build();
        }

        return ResponseEntity.notFound().build();
    }
}
