package com.microservice.backend_for_frontend;

import com.microservice.shared.PostCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BffService {

    @Autowired
    PostAggregateRepository repository;

    public void createPostAggregate(PostCreatedEvent event) {
        var newPostAggregate = PostAggregate.create(event);
        repository.save(newPostAggregate).subscribe();
    }
}
