package com.garage.config;

import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public interface Constants {

    MediaType MEDIA_TYPE_APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);
}
