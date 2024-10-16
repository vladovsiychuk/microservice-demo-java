package com.microservice.post;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    Flux<Post> list() {
        return repository.findAll();
    }

    public Mono<Post> create(PostCommand command) {
        var newPost = Post.create(command);
        return repository.save(newPost)
            .thenReturn(newPost);
    }

    public Mono<Post> update(UUID postId, PostCommand command) {
        return repository.findById(postId)
            .switchIfEmpty(Mono.error(new RuntimeException("Post Not Found")))
            .flatMap(post -> {
               var updatedPost = post.update(command);
               return repository.save(updatedPost);
            });
    }

    public Mono<Boolean> isPrivate(UUID postId) {
        return repository.findById(postId)
            .map(Post::isPrivate);
    }
}
