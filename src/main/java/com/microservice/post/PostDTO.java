package com.microservice.post;

import java.util.UUID;

public record PostDTO(
    UUID id,
    String content
) { }
