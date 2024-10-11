package com.microservice.post;

import org.junit.jupiter.api.Test;
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


        given(this.postService.create(new PostCommand("hello"))).willReturn(Mono.just(post));

        // Perform a POST request and assert the response status
        webTestClient.post()
            .uri("/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new PostCommand("hello"))
            .exchange()
            .expectStatus().isOk();
    }
}
