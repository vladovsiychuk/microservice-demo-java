package com.microservice.comment;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class CommentRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongo = new MongoDBContainer(
        "mongo:latest"
    );

    @Autowired
    CommentRepository repository;

    @Test
    void testEntitySaveAndMapping() {
        var comment = Comment.create(new CommentCommand("test", UUID.randomUUID()), false);
        assert comment.getId() != null;

        repository.save(comment).block();
        Comment savedEntity = repository.findById(comment.getId()).block();

        assert savedEntity != null;
    }
}
