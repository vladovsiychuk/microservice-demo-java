package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentDTO;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

class BffServiceTest {
    @InjectMocks
    private BffService bffService;

    @Mock
    private PostAggregateRepository postAggregateRepository;

    private MockedStatic<PostAggregate> postAggregateMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postAggregateMockedStatic = mockStatic(PostAggregate.class);
    }

    @AfterEach
    void tearDown() {
        postAggregateMockedStatic.close();
    }

    @Nested
    class SuccessfulTests {

        @Test
        void addCommentToPostAggregate_ShouldSucceed() {
            PostAggregate postMock = mock(PostAggregate.class);
            CommentCreatedEvent anyCommentCreatedEvent = anyCommentCreatedEvent();

            when(postAggregateRepository.findById(any(UUID.class))).thenReturn(Mono.just(postMock));
            when(postMock.addComment(any())).thenReturn(postMock);
            when(postAggregateRepository.save(any())).thenReturn(Mono.just(postMock));

            bffService.addCommentToPostAggregate(anyCommentCreatedEvent);

            verify(postAggregateRepository, times(1)).findById(any(UUID.class));
            verify(postMock, times(1)).addComment(any());
            verify(postAggregateRepository, times(1)).save(any());
        }
    }

    private CommentCreatedEvent anyCommentCreatedEvent() {
        return new CommentCreatedEvent(
            1L, new CommentDTO(UUID.randomUUID(), UUID.randomUUID(), "hello")
        );
    }
}
