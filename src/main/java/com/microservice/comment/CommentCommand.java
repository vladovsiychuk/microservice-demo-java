package com.microservice.comment;

import lombok.NonNull;

public record CommentCommand(
    @NonNull String content
) { }
