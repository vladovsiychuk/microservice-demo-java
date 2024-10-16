package com.microservice.backend_for_frontend;

import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostDTO;
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
class PostAggregateRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongo = new MongoDBContainer(
        "mongo:latest"
    );

    @Autowired
    PostAggregateRepository repository;

    @Test
    void testEntitySaveAndMapping() {
        var postAggregate = PostAggregate.create(
            new PostCreatedEvent(1L, new PostDTO(UUID.randomUUID(), "hello", false))
        );
        assert postAggregate.getId() != null;

        repository.save(postAggregate).block();
        PostAggregate savedEntity = repository.findById(postAggregate.getId()).block();

        assert savedEntity != null;
    }
}
