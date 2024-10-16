package com.microservice.shared;

import java.util.UUID;

public record CommentDTO(
    UUID id,
    String content
) {  }
