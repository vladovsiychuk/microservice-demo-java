package com.microservice.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    Flux<Post> list() {
        return repository.findAll();
    }

    public Mono<Post> create(PostCommand command) {
        var newPost = new Post(command);
        return repository.save(newPost)
            .thenReturn(newPost);
    }
}
