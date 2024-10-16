package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @Autowired
    BffService service;

    @EventListener
    void handlePostCreated(PostCreatedEvent event) {
        service.createPostAggregate(event);
    }

    @EventListener
    void handlePostUpdated(PostUpdatedEvent event) {
        service.updatePostAggregate(event);
    }

    @EventListener
    void handleCommentCreated(CommentCreatedEvent event) {
        service.addCommentToPostAggregate(event);
    }

    @EventListener
    void handleCommentUpdate(CommentUpdatedEvent event) {
        service.updateCommentInPostAggregate(event);
    }
}
