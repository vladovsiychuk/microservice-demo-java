package com.microservice.post;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class Post {
    @Id
    private UUID id;
    private String content;
}
