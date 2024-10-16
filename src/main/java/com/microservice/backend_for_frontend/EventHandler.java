package com.microservice.backend_for_frontend;

import com.microservice.shared.PostCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @Autowired
    BffService bffService;

    @EventListener
    void handlePostCreated(PostCreatedEvent event) {
        bffService.createPostAggregate(event);
    }
}
