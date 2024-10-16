package com.microservice.shared;

import java.util.UUID;

public record PostDTO(
    UUID id,
    String content,
    boolean isPrivate
) { }
