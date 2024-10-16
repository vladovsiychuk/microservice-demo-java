package com.microservice.post;

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
    void testEntitySaveAndUpdate() {
        var post = Post.create(new PostCommand("test", false));
        assert post.getId() != null;

        repository.save(post).block();
        Post savedEntity = repository.findById(post.getId()).block();

        assert savedEntity != null;
        assert savedEntity.getContent().equals("test");
        assert !savedEntity.isPrivate();

        var updateCommand = new PostCommand("test mod", true);
        Post updatedPost = savedEntity.update(updateCommand);
        Post updatedEntity = repository.save(updatedPost).block();

        assert updatedEntity != null;
        assert updatedEntity.getContent().equals("test mod");
        assert updatedEntity.isPrivate();
    }
}
