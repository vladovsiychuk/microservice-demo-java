package com.microservice.comment;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts")
public class CommentController {

    @Autowired
    CommentService service;

    @PostMapping("/{id}/comment")
    public Mono<Comment> create(@RequestBody CommentCommand command, @PathVariable UUID id) {
        var newCommand = new CommentCommand(command.content(), id);
        return service.create(newCommand);
    }
}
