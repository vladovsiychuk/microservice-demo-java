package com.microservice.comment;

import java.util.UUID;
import lombok.NonNull;

public record CommentCommand(
    @NonNull String content,
    UUID postId
) { }
