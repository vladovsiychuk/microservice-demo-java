package com.microservice.shared;

public record CommentCreatedEvent(
    long dateCreatedTimestamp,
    CommentDTO comment
) { }
