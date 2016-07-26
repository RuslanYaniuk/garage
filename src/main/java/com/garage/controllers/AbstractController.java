package com.garage.controllers;

import com.garage.config.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@Component
public abstract class AbstractController {

    private static final HttpHeaders COMMON_HEADERS;

    static {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", Constants.MEDIA_TYPE_APPLICATION_JSON_UTF8.toString());
        COMMON_HEADERS = HttpHeaders.readOnlyHttpHeaders(httpHeaders);
    }

    public <T> ResponseEntity badRequest(T body) {
        return new ResponseEntity<>(body, COMMON_HEADERS, BAD_REQUEST);
    }
}
