package com.microservice.post;

import lombok.NonNull;

public record PostCommand(
    @NonNull String content
) { }
