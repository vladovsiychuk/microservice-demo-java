package com.mmorpg.commons;

import java.util.UUID;

public record MobDTO(
    UUID id,
    int currentHealth
) { }
