package com.microservice.comment;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommentRepository extends ReactiveCrudRepository<Comment, UUID> {
}
