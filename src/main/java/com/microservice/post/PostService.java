package com.microservice.post;

import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@SuppressWarnings("unused")
public class PostService {

    @Autowired
    PostRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    Flux<Post> list() {
        return repository.findAll();
    }

    public Mono<Post> create(PostCommand command) {
        var newPost = Post.create(command);
        return repository.save(newPost)
            .doOnSuccess(post ->
                publisher.publishEvent(new PostCreatedEvent(new Date().toInstant().toEpochMilli(), post.toDto()))
            );
    }

    public Mono<Post> update(UUID postId, PostCommand command) {
        return repository.findById(postId)
            .switchIfEmpty(Mono.error(new RuntimeException("Post Not Found")))
            .map(post -> post.update(command))
            .flatMap(repository::save)
            .doOnSuccess(post ->
                publisher.publishEvent(new PostUpdatedEvent(new Date().toInstant().toEpochMilli(), post.toDto()))
            );
    }

    public Mono<Boolean> isPrivate(UUID postId) {
        return repository.findById(postId)
            .map(Post::isPrivate);
    }
}
