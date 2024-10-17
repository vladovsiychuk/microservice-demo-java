package com.microservice.comment;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts/{postId}/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @PostMapping
    public Mono<Comment> create(@RequestBody CommentCommand command, @PathVariable UUID postId) {
        return service.create(postId, command);
    }

    @PutMapping("/{commentId}")
    public Mono<Comment> update(@RequestBody CommentCommand command, @PathVariable UUID commentId) {
        return service.update(commentId, command);
    }
}
