package com.microservice.post;

import java.util.UUID;

public record Post(
    UUID id,
    String content
) { }
