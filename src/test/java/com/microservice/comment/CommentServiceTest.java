package com.microservice.comment;

import com.microservice.post.PostService;
import java.util.UUID;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostService postService;

    private MockedStatic<Comment> commentMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentMockedStatic = mockStatic(Comment.class);
    }

    @AfterEach
    void tearDown() {
        commentMockedStatic.close();
    }

    @Nested
    @DisplayName("Successful comment creation")
    class SuccessfulCreationTests {

        @Test
        @DisplayName("Should call save when comment is created successfully")
        void testCreate_ShouldCallSave() {
            Comment newComment = mock(Comment.class);
            CommentCommand commentCommand = anyCommentCommand();

            commentMockedStatic.when(() -> Comment.create(commentCommand, false)).thenReturn(newComment);
            when(postService.isPrivate(any())).thenReturn(Mono.just(false));
            when(commentRepository.save(any(Comment.class))).thenReturn(Mono.just(newComment));

            Comment result = commentService.create(commentCommand).block();

            assert result != null;

            verify(postService, times(1)).isPrivate(any());
            verify(commentRepository, times(1)).save(any(Comment.class));
        }
    }

    @Nested
    @DisplayName("Failed comment creation")
    class FailedCreationTests {

        @Test
        @DisplayName("Should throw an error when repository fails to save")
        void testCreate_ShouldThrowAnErrorWhenRepositoryFailsTheSave() {
            Comment newComment = mock(Comment.class);
            CommentCommand commentCommand = anyCommentCommand();

            commentMockedStatic.when(() -> Comment.create(commentCommand, false)).thenReturn(newComment);
            when(postService.isPrivate(any())).thenReturn(Mono.error(new RuntimeException("Error")));
            when(commentRepository.save(any(Comment.class))).thenReturn(Mono.just(newComment));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> commentService.create(commentCommand).block());

            assert exception.getMessage().equals("Error");
        }
    }

    private static CommentCommand anyCommentCommand() {
        return new CommentCommand("hello", UUID.randomUUID());
    }
}
