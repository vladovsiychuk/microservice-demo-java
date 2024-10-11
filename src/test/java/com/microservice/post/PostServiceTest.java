package com.microservice.post;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
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

    private MockedStatic<Post> postMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postMockedStatic = mockStatic(Post.class);
    }

    @AfterEach
    void tearDown() {
        postMockedStatic.close();
    }

    @Test
    void testCreate_ShouldCallSave() {
        Post newPost = mock(Post.class);
        PostCommand postCommand = mock(PostCommand.class);

        postMockedStatic.when(() -> Post.create(postCommand)).thenReturn(newPost);
        when(postRepository.save(any(Post.class))).thenReturn(Mono.just(newPost));

        Post result = postService.create(postCommand).block();

        assertNotNull(result);

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testCreate_ShouldThrowAnErrorWhenRepositoryFailsTheSave() {
        Post newPost = mock(Post.class);
        PostCommand postCommand = mock(PostCommand.class); // Mock PostCommand if needed

        postMockedStatic.when(() -> Post.create(postCommand)).thenReturn(newPost);
        when(postRepository.save(any(Post.class))).thenReturn(Mono.error(new RuntimeException("Error")));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.create(postCommand).block());

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void testCreate_ShouldThrowAnErrorWhenPostModelFails() {
        Post newPost = mock(Post.class);
        PostCommand postCommand = mock(PostCommand.class);

        postMockedStatic.when(() -> Post.create(postCommand)).thenThrow(new RuntimeException("Error"));
        when(postRepository.save(any(Post.class))).thenReturn(Mono.just(newPost));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.create(postCommand).block());

        assertEquals("Error", exception.getMessage());
        verify(postRepository, never()).save(any(Post.class));
    }
}
