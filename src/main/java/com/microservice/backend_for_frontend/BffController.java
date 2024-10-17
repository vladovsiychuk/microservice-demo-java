package com.microservice.backend_for_frontend;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts/{postId}")
public class BffController {

    @Autowired
    BffService service;

    @GetMapping
    public Mono<PostAggregate> get(@PathVariable UUID postId) {
        return service.getPostAggregate(postId);
    }
}
