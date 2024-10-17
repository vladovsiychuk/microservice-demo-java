package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BffService {

    @Autowired
    PostAggregateRepository repository;

    @Autowired
    ReactiveRedisOperations<String, PostAggregate> postOps;

    public Mono<PostAggregate> getPostAggregate(UUID postId) {
        return getFromRedisCache(postId)
            .switchIfEmpty(repository.findById(postId));

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
            .flatMap(repository::save)
            .doOnSuccess(this::updateRedisCache)
            .subscribe();
    }

    public void addCommentToPostAggregate(CommentCreatedEvent event) {
        repository.findById(event.comment().postId())
            .map(post -> post.addComment(event))
            .flatMap(repository::save)
            .subscribe();
    }

    public void updateCommentInPostAggregate(CommentUpdatedEvent event) {
        repository.findById(event.comment().postId())
            .map(post -> post.updateComment(event))
            .flatMap(repository::save)
            .subscribe();
    }

    private void updateRedisCache(PostAggregate post) {
        postOps.opsForValue()
            .set(post.getId().toString(), post)
            .subscribe();
    }

    private Mono<PostAggregate> getFromRedisCache(UUID postId) {
        return postOps.opsForValue()
            .get(postId.toString());
    }
}
