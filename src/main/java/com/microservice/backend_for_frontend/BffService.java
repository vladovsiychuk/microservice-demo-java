package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BffService {

    private final PostAggregateRepository repository;

    private final ReactiveRedisOperations<UUID, PostAggregate> postOps;

    public Mono<PostAggregate> getPostAggregate(UUID postId) {
        return repository.findById(postId);
    }

    public void createPostAggregate(PostCreatedEvent event) {
        var newPostAggregate = PostAggregate.create(event);
        repository.save(newPostAggregate)
            .doOnSuccess(this::updateRedisCache)
            .subscribe();
    }

    public void updatePostAggregate(PostUpdatedEvent event) {

        repository.findById(event.post().id())
            .map(post -> post.update(event))
            .flatMap(updatedPost -> repository.save(updatedPost))
            .doOnSuccess(this::updateRedisCache)
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

    private void updateRedisCache(PostAggregate post) {
        postOps.opsForValue()
            .set(post.getId(), post)
            .subscribe();
    }
}
