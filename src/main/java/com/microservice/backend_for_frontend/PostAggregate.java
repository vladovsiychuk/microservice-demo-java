package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.PostCreatedEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PostAggregate {
    @Id private UUID id;
    private String content;
    private Boolean isPrivate;
    private List<CommentItem> comments = new ArrayList<>();

    public static PostAggregate create(PostCreatedEvent event) {
        return new PostAggregate(
            UUID.randomUUID(),
            event.post().content(),
            event.post().isPrivate(),
            null
        );
    }

    public PostAggregate addComment(CommentCreatedEvent event) {
        var newComment = CommentItem.create(event);
        comments.add(newComment);
        return this;
    }
}
