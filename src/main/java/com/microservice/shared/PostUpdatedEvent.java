package com.microservice.shared;

public record PostUpdatedEvent(
    long dateCreatedTimestamp,
    PostDTO post
) { }
