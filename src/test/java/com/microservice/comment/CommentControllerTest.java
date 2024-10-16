package com.microservice.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SuppressWarnings("unused")
@WebFluxTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldCreateCommentAndReturnJsonResponse() throws JsonProcessingException {
        Comment comment = Comment.create(new CommentCommand("hello", UUID.randomUUID()), false);
        var command = Map.of(
            "content", "hello",
            "postId", comment.getPostId()
        );

        assert comment.getId() != null;
        var expectedBody = Map.of(
            "id", comment.getId().toString(),
            "content", "hello",
            "postId", comment.getPostId()
        );

        given(this.commentService.create(any())).willReturn(Mono.just(comment));

        webTestClient.post()
            .uri("/v1/posts/{postId}/comment", comment.getPostId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(command)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json(mapper.writeValueAsString(expectedBody), true);
    }
}
