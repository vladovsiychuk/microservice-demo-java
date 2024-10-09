package com.microservice.post;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostService {

    Flux<Post> create() {
        return Flux.fromIterable(List.of(new Post(UUID.randomUUID(), "foo")));
    }
}
