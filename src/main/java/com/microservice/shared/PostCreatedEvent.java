package com.microservice.shared;

public record PostCreatedEvent (
    long dateCreatedTimestamp,
    PostDTO post
) { }
