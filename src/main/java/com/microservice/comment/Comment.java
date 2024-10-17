package com.microservice.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.shared.CommentDTO;
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

    public static Comment create(CommentCommand command, UUID postId, boolean postIsPrivate) {
        if (postIsPrivate)
            throw new RuntimeException("Comments cannot be added to private posts.");

        return new Comment(UUID.randomUUID(), postId, command.content(), true);
    }

    public Comment update(CommentCommand command) {
        content = command.content();
        return this;
    }

    public CommentDTO toDto() {
        return new CommentDTO(id, postId, content);
    }
}
