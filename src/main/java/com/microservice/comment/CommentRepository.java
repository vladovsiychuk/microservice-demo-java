package com.microservice.comment;

import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<Comment, UUID> {
}
