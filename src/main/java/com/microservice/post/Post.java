package com.microservice.post;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor
public class Post {
    @Id
    private UUID id;
    private String content;

    public Post(PostCommand command) {
        id = UUID.randomUUID();
        content = command.content();
    }
}
