package com.microservice.commons;

import java.util.UUID;

public record MobDTO(
    UUID id,
    int currentHealth
) { }
