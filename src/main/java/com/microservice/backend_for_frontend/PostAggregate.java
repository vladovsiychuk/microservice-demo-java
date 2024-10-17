package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import com.microservice.shared.PostCreatedEvent;
import com.microservice.shared.PostUpdatedEvent;
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
    private List<CommentItem> comments;

    public static PostAggregate create(PostCreatedEvent event) {
        return new PostAggregate(
            event.post().id(),
            event.post().content(),
            event.post().isPrivate(),
            new ArrayList<>()
        );
    }

    public PostAggregate update(PostUpdatedEvent event) {
        content = event.post().content();
        isPrivate = event.post().isPrivate();
        return this;
    }

    public PostAggregate addComment(CommentCreatedEvent event) {
        var newComment = CommentItem.create(event);
        comments.add(newComment);
        return this;
    }

    public PostAggregate updateComment(CommentUpdatedEvent event) {
        var commentToUpdate = comments.stream()
            .filter(comment -> comment.getId().equals(event.comment().id()))
            .findFirst();

        commentToUpdate.ifPresent(commentItem -> commentItem.update(event));
        return this;
    }
}
