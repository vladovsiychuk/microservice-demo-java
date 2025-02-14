package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentDTO;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostDTO;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class PostAggregateTest {

    @Test
    void updateTest() {
        var postId = UUID.randomUUID();
        var commentId = UUID.randomUUID();
        var createdPost = PostAggregate.create(
            new PostCreatedEvent(1L, new PostDTO(postId, "hello", false))
        );

        createdPost.addComment(
            new CommentCreatedEvent(
                1L, new CommentDTO(commentId, UUID.randomUUID(), "hello")
            )
        );

        PostAggregate updatedPost = createdPost.updateComment(
            new CommentUpdatedEvent(1L, new CommentDTO(commentId, UUID.randomUUID(), "hello mod"))
        );

        assert updatedPost.getComments().getFirst().getContent().equals("hello mod");
        assert updatedPost.getId().equals(postId);
    }
}
