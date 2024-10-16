package com.microservice.comment;

import com.microservice.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CommentService {

    @Autowired
    CommentRepository repository;

    @Autowired
    PostService postService;

    public Mono<Comment> create(CommentCommand command) {
        return postService.isPrivate(command.postId())
            .map( postIsPrivate -> Comment.create(command, postIsPrivate))
            .flatMap(newComment -> repository.save(newComment));
    }
}
