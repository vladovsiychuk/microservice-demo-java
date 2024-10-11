package com.microservice.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Post implements Persistable<UUID> {
    @Id private UUID id;
    private String content;
    @Transient @JsonIgnore
    private boolean isNew;

    public Post(PostCommand command) {
        id = UUID.randomUUID();
        content = command.content();
        isNew = true;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
