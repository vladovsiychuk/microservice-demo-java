package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BffService {

    @Autowired
    PostAggregateRepository repository;

    public Mono<PostAggregate> getPostAggregate(UUID postId) {
        return repository.findById(postId);
    }

    public void createPostAggregate(PostCreatedEvent event) {
        var newPostAggregate = PostAggregate.create(event);
        repository.save(newPostAggregate).subscribe();
    }

    public void updatePostAggregate(PostUpdatedEvent event) {
        repository.findById(event.post().id())
            .map(post -> post.update(event))
            .flatMap(updatedPost -> repository.save(updatedPost))
            .subscribe();
    }

    public void addCommentToPostAggregate(CommentCreatedEvent event) {
        repository.findById(event.comment().postId())
            .map(post -> post.addComment(event))
            .flatMap(updatedPost -> repository.save(updatedPost))
            .subscribe();
    }

    public void updateCommentInPostAggregate(CommentUpdatedEvent event) {
        repository.findById(event.comment().postId())
            .map(post -> post.updateComment(event))
            .flatMap(updatedPost -> repository.save(updatedPost))
            .subscribe();
    }
}
