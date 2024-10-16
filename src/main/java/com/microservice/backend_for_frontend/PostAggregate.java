package com.microservice.backend_for_frontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.shared.PostCreatedEvent;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PostAggregate implements Persistable<UUID> {
    @Id private UUID id;
    private String content;
    private Boolean isPrivate;
    private List<CommentItem> comments;

    @Transient
    @JsonIgnore
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static PostAggregate create(PostCreatedEvent event) {
        return new PostAggregate(
            UUID.randomUUID(),
            event.post().content(),
            event.post().isPrivate(),
            null,
            true
        );
    }
}
