package com.microservice.post;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostService {

    Flux<PostDTO> create() {
        return Flux.fromIterable(List.of(new PostDTO(UUID.randomUUID(), "foo")));
    }
}
