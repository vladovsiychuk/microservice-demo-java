package com.microservice.backend_for_frontend;

import com.microservice.shared.CommentCreatedEvent;
import com.microservice.shared.CommentUpdatedEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentItem {
    @Id
    private UUID id;
    private String content;

    public static CommentItem create(CommentCreatedEvent event) {
        return new CommentItem(event.comment().id(), event.comment().content());
    }

    public void update(CommentUpdatedEvent event) {
        content = event.comment().content();
    }
}
