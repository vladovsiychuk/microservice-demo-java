package com.microservice.post;

import java.util.UUID;
import org.springframework.data.annotation.Id;

public record Post(
    @Id
    UUID id,
    String content
) { }
