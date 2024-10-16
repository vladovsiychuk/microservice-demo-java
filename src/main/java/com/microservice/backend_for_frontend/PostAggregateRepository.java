package com.microservice.backend_for_frontend;

import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostAggregateRepository extends ReactiveMongoRepository<PostAggregate, UUID> {
}
