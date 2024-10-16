package com.microservice.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@AllArgsConstructor @NoArgsConstructor
public class Comment implements Persistable<UUID> {
    @Id private UUID id;
    @NonNull private UUID postId;
    private String content;

    @Transient
    @JsonIgnore
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static Comment create(CommentCommand command, boolean postIsPrivate) {
        if (postIsPrivate)
            throw new RuntimeException("Comments cannot be added to private posts.");

        return new Comment(UUID.randomUUID(), command.postId(), command.content(), true);
    }
}
