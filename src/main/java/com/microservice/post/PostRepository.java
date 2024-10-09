package com.microservice.post;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, UUID> {
}
