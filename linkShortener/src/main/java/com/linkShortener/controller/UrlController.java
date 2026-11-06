package com.linkShortener.controller;

import com.linkShortener.model.dto.UrlRequest;
import com.linkShortener.model.dto.UrlResponse;
import com.linkShortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
