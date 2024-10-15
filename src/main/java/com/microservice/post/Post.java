package com.microservice.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@AllArgsConstructor @NoArgsConstructor
public class Post implements Persistable<UUID> {
    @Id private UUID id;
    private String content;
    private boolean isPrivate;

    @Transient
    @JsonIgnore
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static Post create(PostCommand command) {
        if (command.content().length() > 100)
            throw new RuntimeException("Content too long.");

        return new Post(UUID.randomUUID(), command.content(), command.isPrivate(), true);
    }
}
