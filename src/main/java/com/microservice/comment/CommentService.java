package com.microservice.comment;

import com.microservice.post.PostService;
import com.microservice.shared.CommentUpdatedEvent;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CommentService {

    @Autowired
    CommentRepository repository;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    PostService postService;

    public Mono<Comment> create(CommentCommand command) {
        return postService.isPrivate(command.postId())
            .map( postIsPrivate -> Comment.create(command, postIsPrivate))
            .flatMap(newComment -> repository.save(newComment));
    }

    public Mono<Comment> update(UUID commentId, CommentCommand command) {
        return repository.findById(commentId)
            .map(comment -> comment.update(command))
            .flatMap(updatedComment -> repository.save(updatedComment))
            .doOnSuccess(comment ->
                publisher.publishEvent(new CommentUpdatedEvent(new Date().toInstant().toEpochMilli(), comment.toDto()))
            );
    }
}
