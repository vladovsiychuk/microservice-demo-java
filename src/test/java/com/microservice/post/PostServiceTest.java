package com.microservice.post;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_ShouldCallSave() {
        Post newPost = mock(Post.class);
        PostCommand postCommand = mock(PostCommand.class); // Mock PostCommand if needed

        when(postRepository.save(any(Post.class))).thenReturn(Mono.just(newPost));

        Post result = postService.create(postCommand).block();

        assertNotNull(result);

        verify(postRepository, times(1)).save(any(Post.class)); // Verify save was called once
    }
}
