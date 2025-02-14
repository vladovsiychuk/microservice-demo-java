package com.microservice.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
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
@WebFluxTest(PostController.class)
class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PostService postService;

    ObjectMapper mapper = new ObjectMapper();

    @Nested
    class createTests {

        @Test
        void shouldCreatePostAndReturnJsonResponse() throws JsonProcessingException {
            Post post = Post.create(new PostCommand("hello", false));
            var command = Map.of(
                "content", "hello"
            );

            assert post.getId() != null;
            var expectedBody = Map.of(
                "id", post.getId().toString(),
                "content", "hello",
                "private", false
            );

            given(postService.create(any())).willReturn(Mono.just(post));

            webTestClient.post()
                .uri("/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(command)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(mapper.writeValueAsString(expectedBody), true);
        }

        @Test
        void shouldReturnErrorStatusCodeOnCreate() {
            given(postService.create(any())).willReturn(Mono.error(new RuntimeException("Error")));

            webTestClient.post()
                .uri("/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(anyPostCommand())
                .exchange()
                .expectStatus().is5xxServerError();
        }
    }

    @Nested
    class updateTests {

        @Test
        void shouldUpdatePostAndReturnJsonResponse() throws JsonProcessingException {
            Post post = Post.create(new PostCommand("hello", true));
            var command = Map.of(
                "content", "hello",
                "isPrivate", true
            );

            assert post.getId() != null;
            var expectedBody = Map.of(
                "id", post.getId().toString(),
                "content", "hello",
                "private", true
            );

            given(postService.update(any(), any())).willReturn(Mono.just(post));

            webTestClient.put()
                .uri("/v1/posts/{postId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(command)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(mapper.writeValueAsString(expectedBody), true);
        }

        @Test
        void shouldReturnErrorStatusCodeOnUpdate() {
            given(postService.update(any(), any())).willReturn(Mono.error(new RuntimeException("Error")));

            webTestClient.put()
                .uri("/v1/posts/{postId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(anyPostCommand())
                .exchange()
                .expectStatus().is5xxServerError();
        }
    }

    private PostCommand anyPostCommand() {
        return new PostCommand("foo", false);
    }
}
