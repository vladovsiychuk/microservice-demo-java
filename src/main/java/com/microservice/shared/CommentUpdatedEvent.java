package com.microservice.shared;

public record CommentUpdatedEvent(
    long dateCreatedTimestamp,
    CommentDTO comment
) { }
