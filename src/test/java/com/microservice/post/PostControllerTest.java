package com.microservice.post;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(PostController.class)
class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PostService postService;

    @Test
    void abc() {
        Post post = Post.create(new PostCommand("hello"));
        var command =
            """
            {
                "content": "hello"
            }
            """;

        var expectedBody =
            """
            {
                "content": "hello"
            }
            """;

        given(this.postService.create(any(PostCommand.class))).willReturn(Mono.just(post));

        webTestClient.post()
            .uri("/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(command)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json(expectedBody);
    }
}
