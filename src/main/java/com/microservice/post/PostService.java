package com.microservice.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    Flux<Post> list() {
        return repository.findAll();
    }
}
