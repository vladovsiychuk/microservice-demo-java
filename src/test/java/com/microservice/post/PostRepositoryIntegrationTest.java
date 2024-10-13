package com.microservice.post;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class PostRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
        "postgres:latest"
    );

    @Autowired
    PostRepository repository;

    @Test
    void testEntitySaveAndMapping() {
        var postId = UUID.randomUUID();
        Post post = new Post(postId, "test", true);

        repository.save(post).block();
        Post savedEntity = repository.findById(postId).block();

        assert savedEntity != null;
    }
}
