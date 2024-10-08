package com.microservice.shared;

import java.util.UUID;

public record MobDTO(
    UUID id,
    int currentHealth
) { }
