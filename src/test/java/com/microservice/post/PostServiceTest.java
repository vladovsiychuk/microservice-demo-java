package com.microservice.post;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("Successful post creation")
    class SuccessfulCreationTests {

        @Test
        @DisplayName("Should call save when post is created successfully")
        void testCreate_ShouldCallSave() {
            Post newPost = mock(Post.class);
            PostCommand postCommand = anyPostCommand();

            postMockedStatic.when(() -> Post.create(postCommand)).thenReturn(newPost);
            when(postRepository.save(any())).thenReturn(Mono.just(newPost));

            Post result = postService.create(postCommand).block();

            assert result != null;

            verify(postRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("Failed post creation")
    class FailedCreationTests {

        @Test
        @DisplayName("Should throw an error when repository fails to save")
        void testCreate_ShouldThrowAnErrorWhenRepositoryFailsTheSave() {
            Post newPost = mock(Post.class);
            PostCommand postCommand = anyPostCommand();

            postMockedStatic.when(() -> Post.create(postCommand)).thenReturn(newPost);
            when(postRepository.save(any())).thenReturn(Mono.error(new RuntimeException("Error")));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.create(postCommand).block());

            assert exception.getMessage().equals("Error");
        }

        @Test
        @DisplayName("Should throw an error when Post.create() fails")
        void testCreate_ShouldThrowAnErrorWhenPostModelFails() {
            PostCommand postCommand = anyPostCommand();

            postMockedStatic.when(() -> Post.create(postCommand)).thenThrow(new RuntimeException("Error"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.create(postCommand).block());

            assert exception.getMessage().equals("Error");
            verify(postRepository, never()).save(any(Post.class));
        }
    }

    private static PostCommand anyPostCommand() {
        return new PostCommand("hello", false);
    }
}
