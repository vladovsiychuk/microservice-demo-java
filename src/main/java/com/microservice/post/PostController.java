package com.microservice.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    PostService service;

    @GetMapping
    public Flux<Post> list() {
        return service.list();
    }
}
